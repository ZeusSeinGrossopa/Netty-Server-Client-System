package de.zeus.server;

import de.zeus.server.netty.ClientChannelInitalizer;
import de.zeus.server.packet.Packet;
import de.zeus.server.packet.PacketManager;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.util.ArrayList;
import java.util.Objects;

@ChannelHandler.Sharable
public class NettyServer {

    private static NettyServer instance;

    private NioEventLoopGroup nioEventLoopGroup;
    private ServerBootstrap bootstrap;
    private ChannelFuture channelFuture;

    private PacketManager packetManager;
    private ArrayList<Channel> registeredChannels;

    public NettyServer() {
        instance = this;
    }

    public final void start(String ip, int port) throws RuntimeException {
        reset();
        System.out.println("Starting server on " + ip + ":" + port);

        bootstrap = new ServerBootstrap();
        bootstrap.group(nioEventLoopGroup);
        bootstrap.channel(NioServerSocketChannel.class);
        bootstrap.childHandler(new ClientChannelInitalizer(this));

        try {
            bootstrap.childOption(ChannelOption.TCP_NODELAY, true)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .option(ChannelOption.AUTO_CLOSE, true)
                    .option(ChannelOption.SO_REUSEADDR, true);
        } catch(ChannelException ignored) {}

        try {
            channelFuture = bootstrap.localAddress(ip, port).bind().syncUninterruptibly();

            if(channelFuture.isSuccess())
                System.out.println("Server started successfully");
            else
                throw new RuntimeException("Unable to start server");
        } catch(Exception e) {
            System.err.println("Could not start server on " + ip + ":" + port);

            throw new RuntimeException("Unable to start server", e);
        }
    }

    public final void stop() {
        for (Channel channel : getRegisteredChannels()) {
            if(channel != null && channel.isOpen()) {
                channel.close();
            }
        }

        if(nioEventLoopGroup != null)
            nioEventLoopGroup.shutdownGracefully();

        if(channelFuture != null)
            channelFuture.channel().close();

        reset();
    }

    public void reset() {
        nioEventLoopGroup = new NioEventLoopGroup(1);
        registeredChannels = new ArrayList<>();
        bootstrap = null;
        packetManager = new PacketManager(new PacketListener());
    }

    public void sendPacket(Packet packet) {
        Objects.requireNonNull(packet, "Packet cannot be null");

        System.out.println("[OUT] " + packet.getClass().getSimpleName() + " " + packet.getID());

        for (Channel channel : getRegisteredChannels()) {
            if(!channel.isOpen())
                continue;

            channel.writeAndFlush(packet).addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    if(!channelFuture.isSuccess()) {
                        channelFuture.cause().printStackTrace();
                    }
                }
            });
        }
    }

    public void sendPacketFor(Channel channel, Packet packet) {
        Objects.requireNonNull(packet, "Packet cannot be null");
        Objects.requireNonNull(channel, "Channel cannot be null");

        System.out.println("[OUT] " + packet.getClass().getSimpleName() + " " + packet.getID());

        if(!channel.isOpen())
            return;

        channel.writeAndFlush(packet).addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if(!channelFuture.isSuccess()) {
                    channelFuture.cause().printStackTrace();
                }
            }
        });
    }

    public NioEventLoopGroup getNioEventLoopGroup() {
        return nioEventLoopGroup;
    }

    public ServerBootstrap getBootstrap() {
        return bootstrap;
    }

    public ArrayList<Channel> getRegisteredChannels() {
        return registeredChannels;
    }

    public ChannelFuture getChannelFuture() {
        return channelFuture;
    }

    public PacketManager getPacketManager() {
        return packetManager;
    }

    public static NettyServer getInstance() {
        return instance;
    }
}