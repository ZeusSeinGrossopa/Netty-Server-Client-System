package de.zeus.server;

import de.zeus.server.packet.PacketHandler;
import de.zeus.server.packet.packets.*;

public class PacketListener implements PacketHandler {

    @Override
    public void handle(PacketPing packetPing) {
        NettyServer.getInstance().sendPacketFor(packetPing.getChannel(), new PacketPong());
    }

    @Override
    public void handle(PacketPong packetPong) {}

    @Override
    public void handle(PacketMessage packetMessage) {
        System.out.println("Received message: " + packetMessage.getMessage());
    }
}