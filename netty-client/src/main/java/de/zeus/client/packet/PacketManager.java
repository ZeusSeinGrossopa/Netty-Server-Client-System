package de.zeus.client.packet;

import de.zeus.client.packet.packets.*;

import java.util.ArrayList;

public class PacketManager {

    private final ArrayList<Packet> packets;
    private PacketHandler packetHandler;

    public PacketManager(PacketHandler packetHandler) {
        packets = new ArrayList<>();
        this.packetHandler = packetHandler;


        registerPacket(new PacketPing());
        registerPacket(new PacketPong());
        registerPacket(new PacketMessage());
    }

    public void registerPacket(Packet packet) {
        packets.add(packet);
    }

    public Packet getPacket(int id) {
        return packets.stream().filter(e -> e.getID() == id).findFirst().orElseThrow(() -> new IllegalArgumentException("Packet with id " + id + " not found!"));
    }

    public int getPacketID(Packet packet) {
        return packets.indexOf(packet);
    }

    public ArrayList<Packet> getPackets() {
        return packets;
    }

    public PacketHandler getPacketHandler() {
        return packetHandler;
    }

    public void setPacketHandler(PacketHandler packetHandler) {
        this.packetHandler = packetHandler;
    }
}
