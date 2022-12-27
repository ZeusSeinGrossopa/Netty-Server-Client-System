package de.zeus.server.packet;

import de.zeus.server.packet.packets.*;
import io.netty.channel.Channel;

public interface PacketHandler {

    default void handle(Object obj, Channel channel) {
        if(obj instanceof Packet packet) {
            packet.setChannel(channel);
            packet.handle(this);
        } else
            throw new IllegalArgumentException("The received object is not a packet! Object Type: " + obj.getClass().getName());
    }

    void handle(PacketPing packetPing);

    void handle(PacketPong packetPong);

    void handle(PacketMessage packetMessage);
}