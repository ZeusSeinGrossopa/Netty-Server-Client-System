package de.zeus.server.netty;

import de.zeus.server.NettyServer;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.nio.NioSocketChannel;

public class ClientChannelInitalizer extends ChannelInitializer<NioSocketChannel> {

    private final NettyServer nettyServer;

    public ClientChannelInitalizer(NettyServer nettyServer) {
        this.nettyServer = nettyServer;
    }

    @Override
    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
        this.nettyServer.getRegisteredChannels().add(nioSocketChannel);

        nioSocketChannel.pipeline()
                .addLast("handler", new ChannelReader(nettyServer));
    }

    public NettyServer getNettyServer() {
        return nettyServer;
    }
}