package de.zeus.server.packet;

import de.zeus.server.utils.PacketBuffer;

public abstract class Packet {

    public abstract void write(PacketBuffer buffer);

    public abstract void read(PacketBuffer buffer);

    public abstract void handle(PacketHandler packetHandler);

    public abstract int getID();

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Packet && ((Packet) obj).getID() == getID();
    }
}