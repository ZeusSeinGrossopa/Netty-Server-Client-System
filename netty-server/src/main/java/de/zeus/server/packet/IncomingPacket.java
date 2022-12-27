package de.zeus.server.packet;

import io.netty.channel.Channel;

public class IncomingPacket {

    private Channel channel;

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public Channel getChannel() {
        return channel;
    }
}