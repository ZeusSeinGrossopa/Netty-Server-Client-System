package de.zeus.server.packet.packets;

import de.zeus.server.packet.Packet;
import de.zeus.server.packet.PacketHandler;
import de.zeus.server.utils.PacketBuffer;

public class PacketPing extends Packet {

    @Override
    public void write(PacketBuffer buffer) {}

    @Override
    public void read(PacketBuffer buffer) {}

    @Override
    public void handle(PacketHandler packetHandler) {
        packetHandler.handle(this);
    }

    @Override
    public int getID() {
        return 0;
    }
}