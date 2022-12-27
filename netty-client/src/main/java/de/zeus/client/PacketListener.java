package de.zeus.client;

import de.zeus.client.packet.PacketHandler;
import de.zeus.client.packet.packets.*;

public class PacketListener implements PacketHandler {

    @Override
    public void handle(PacketPing packetPing) {}

    @Override
    public void handle(PacketPong packetPong) {
        System.out.println("Connected to the server!");
    }

    @Override
    public void handle(PacketMessage packetMessage) {
        System.out.println("Received message: " + packetMessage.getMessage());
    }
}