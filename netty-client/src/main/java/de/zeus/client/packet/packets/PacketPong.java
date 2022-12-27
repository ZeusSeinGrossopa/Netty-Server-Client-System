package de.zeus.client.packet.packets;

import de.zeus.client.packet.Packet;
import de.zeus.client.packet.PacketHandler;
import de.zeus.client.utils.PacketBuffer;

public class PacketPong extends Packet {

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
        return 1;
    }
}