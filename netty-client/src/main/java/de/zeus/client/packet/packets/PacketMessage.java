package de.zeus.client.packet.packets;

import de.zeus.client.packet.Packet;
import de.zeus.client.packet.PacketHandler;
import de.zeus.client.utils.PacketBuffer;

public class PacketMessage extends Packet {
    
    private String message;

    public PacketMessage() {}

    public PacketMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public void write(PacketBuffer buffer) {
        buffer.writeString(message);
    }

    @Override
    public void read(PacketBuffer buffer) {
        message = buffer.readString(); 
    }

    @Override
    public void handle(PacketHandler packetHandler) {
        packetHandler.handle(this);
    }

    @Override
    public int getID() {
        return 2;
    }
}
