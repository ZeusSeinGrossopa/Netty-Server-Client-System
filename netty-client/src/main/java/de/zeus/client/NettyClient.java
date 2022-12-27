package de.zeus.client;

import de.zeus.client.netty.ServerChannelInitializer;
import de.zeus.client.packet.Packet;
import de.zeus.client.packet.PacketManager;
import de.zeus.client.packet.packets.PacketPing;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

@ChannelHandler.Sharable
public class NettyClient {

    private static NettyClient instance;

    private Bootstrap bootstrap;
    private NioSocketChannel nioSocketChannel;
    private ChannelFuture channelFuture;
    private NioEventLoopGroup nioEventLoopGroup;

    private PacketManager packetManager;

    public NettyClient() {
        instance = this;
    }

    public void connect(String ip, int port) {
        reset();
        System.out.println("Connecting to " + ip + ":" + port);

        bootstrap = new Bootstrap();
        bootstrap.group(nioEventLoopGroup);
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.handler(new ServerChannelInitializer(this));

        try {
            bootstrap.option(ChannelOption.TCP_NODELAY, true)
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000)
                    .option(ChannelOption.SO_KEEPALIVE, true);
        } catch(ChannelException ignored) {}

        try {
            channelFuture = bootstrap.connect(ip, port).syncUninterruptibly();

            if(channelFuture.isSuccess()) {
                System.out.println("Connected successfully");
                sendPacket(new PacketPing());
            } else
                throw new RuntimeException("Unable to connect");
        } catch(Exception e) {
            System.err.println("Could not connect to " + ip + ":" + port);

            throw new RuntimeException("Unable to connect to server", e);
        }
    }

    public void disconnect() {
        if(nioSocketChannel != null && nioSocketChannel.isOpen())
            nioSocketChannel.close();

        if(channelFuture != null)
            channelFuture.channel().close();

        if(nioEventLoopGroup != null)
            nioEventLoopGroup.shutdownGracefully();

        reset();
    }

    public void sendPacket(Packet packet) {
        if (packet == null)
            throw new NullPointerException("Packet cannot be null");

        System.out.println("[OUT] " + packet.getClass().getSimpleName() + " " + packet.getID());

        nioSocketChannel.writeAndFlush(packet).addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                if (!channelFuture.isSuccess()) {
                    channelFuture.cause().printStackTrace();
                }
            }
        });
    }

    public void reset() {
        bootstrap = new Bootstrap();
        nioSocketChannel = new NioSocketChannel();
        channelFuture = null;
        nioEventLoopGroup = new NioEventLoopGroup(1);
        packetManager = new PacketManager(new PacketListener());
    }

    public Bootstrap getBootstrap() {
        return bootstrap;
    }

    public NioSocketChannel getNioSocketChannel() {
        return nioSocketChannel;
    }

    public void setNioSocketChannel(NioSocketChannel nioSocketChannel) {
        this.nioSocketChannel = nioSocketChannel;
    }

    public ChannelFuture getChannelFuture() {
        return channelFuture;
    }

    public static NettyClient getInstance() {
        return instance;
    }

    public NioEventLoopGroup getNioEventLoopGroup() {
        return nioEventLoopGroup;
    }

    public PacketManager getPacketManager() {
        return packetManager;
    }
}