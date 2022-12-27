package de.zeus.client.netty.pipelines;

import de.zeus.client.NettyClient;
import de.zeus.client.packet.Packet;
import de.zeus.client.utils.PacketBuffer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.io.IOException;
import java.util.List;

public class PacketDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        PacketBuffer packetBuffer = new PacketBuffer(byteBuf);

        if (packetBuffer.readableBytes() < 1) {
            return;
        }

        int packetID = packetBuffer.readVarIntFromBuffer();
        Packet packet = NettyClient.getInstance().getPacketManager().getPacket(packetID);

        System.out.println("[IN] " + packetID + " " + packet.getClass().getSimpleName());
        packet.read(packetBuffer);

        if (packetBuffer.readableBytes() > 0) {
            throw new IOException("Packet  (" + packet.getClass().getSimpleName() + ") was larger than expected, found " + packetBuffer.readableBytes() + " bytes extra whilst reading packet " + packet);
        }

        list.add(packet);
    }
}