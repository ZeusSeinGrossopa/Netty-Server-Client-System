package de.zeus.server.netty;

import de.zeus.server.NettyServer;
import de.zeus.server.netty.pipelines.PacketDecoder;
import de.zeus.server.netty.pipelines.PacketEncoder;
import de.zeus.server.netty.pipelines.PacketPrepender;
import de.zeus.server.netty.pipelines.PacketSplitter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;

import java.util.concurrent.TimeUnit;

public class ClientChannelInitalizer extends ChannelInitializer<NioSocketChannel> {

    private final NettyServer nettyServer;

    public ClientChannelInitalizer(NettyServer nettyServer) {
        this.nettyServer = nettyServer;
    }

    @Override
    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
        this.nettyServer.getRegisteredChannels().add(nioSocketChannel);

        nioSocketChannel.pipeline()
                .addLast("timeout", new ReadTimeoutHandler(365L, TimeUnit.DAYS)) //set this timeout to another value if you want
                .addLast("prepender", new PacketPrepender())
                .addLast("decoder", new PacketDecoder())
                .addLast("splitter", new PacketSplitter())
                .addLast("encoder", new PacketEncoder())
                .addLast("handler", new ChannelReader(nettyServer));
    }

    public NettyServer getNettyServer() {
        return nettyServer;
    }
}