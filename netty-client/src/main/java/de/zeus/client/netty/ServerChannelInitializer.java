package de.zeus.client.netty;

import de.zeus.client.NettyClient;
import de.zeus.client.netty.pipelines.PacketDecoder;
import de.zeus.client.netty.pipelines.PacketEncoder;
import de.zeus.client.netty.pipelines.PacketPrepender;
import de.zeus.client.netty.pipelines.PacketSplitter;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;

import java.util.concurrent.TimeUnit;

public class ServerChannelInitializer extends io.netty.channel.ChannelInitializer<NioSocketChannel> {

    private final NettyClient nettyClient;

    public ServerChannelInitializer(NettyClient nettyClient) {
        this.nettyClient = nettyClient;
    }

    @Override
    protected void initChannel(NioSocketChannel channel) throws Exception {
        nettyClient.setNioSocketChannel(channel);

        channel.pipeline()
                .addLast("timeout", new ReadTimeoutHandler(365L, TimeUnit.DAYS)) //set this timeout to another value if you want
                .addLast("prepender", new PacketPrepender())
                .addLast("decoder", new PacketDecoder())
                .addLast("splitter", new PacketSplitter())
                .addLast("encoder", new PacketEncoder())
                .addLast("handler", new ChannelReader(nettyClient));
    }

    public NettyClient getNettyClient() {
        return nettyClient;
    }
}
