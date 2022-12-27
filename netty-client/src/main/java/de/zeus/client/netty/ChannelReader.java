package de.zeus.client.netty;

import de.zeus.client.NettyClient;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ChannelReader extends SimpleChannelInboundHandler<Object> {

    private final NettyClient nettyClient;

    public ChannelReader(NettyClient nettyClient) {
        this.nettyClient = nettyClient;
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        nettyClient.disconnect();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channel, Object object) throws Exception {
        NettyClient.getInstance().getPacketManager().getPacketHandler().handle(object);
    }
}
