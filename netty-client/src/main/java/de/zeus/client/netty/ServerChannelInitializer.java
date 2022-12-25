package de.zeus.client.netty;

import de.zeus.client.NettyClient;
import io.netty.channel.socket.nio.NioSocketChannel;

public class ServerChannelInitializer extends io.netty.channel.ChannelInitializer<NioSocketChannel> {

    private final NettyClient nettyClient;

    public ServerChannelInitializer(NettyClient nettyClient) {
        this.nettyClient = nettyClient;
    }

    @Override
    protected void initChannel(NioSocketChannel channel) throws Exception {
        nettyClient.setNioSocketChannel(channel);

        channel.pipeline()
                .addLast("handler", new ChannelReader(nettyClient));
    }

    public NettyClient getNettyClient() {
        return nettyClient;
    }
}
