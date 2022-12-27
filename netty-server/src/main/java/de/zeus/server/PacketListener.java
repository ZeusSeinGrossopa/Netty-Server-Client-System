package de.zeus.server;

import de.zeus.server.packet.PacketHandler;
import de.zeus.server.packet.packets.PacketPing;
import de.zeus.server.packet.packets.PacketPong;

public class PacketListener implements PacketHandler {

    @Override
    public void handle(PacketPing packetPing) {
        NettyServer.getInstance().sendPacket(new PacketPong());
    }

    @Override
    public void handle(PacketPong packetPong) {}
}