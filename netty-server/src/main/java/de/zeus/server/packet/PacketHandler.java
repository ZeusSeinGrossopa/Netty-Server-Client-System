package de.zeus.server.packet;

import de.zeus.server.packet.packets.*;

public interface PacketHandler {

    default void handle(Object obj) {
        if(obj instanceof Packet packet) {
            packet.handle(this);
        } else
            throw new IllegalArgumentException("The received object is not a packet! Object Type: " + obj.getClass().getName());
    }

    void handle(PacketPing packetPing);

    void handle(PacketPong packetPong);

    void handle(PacketMessage packetMessage);
}