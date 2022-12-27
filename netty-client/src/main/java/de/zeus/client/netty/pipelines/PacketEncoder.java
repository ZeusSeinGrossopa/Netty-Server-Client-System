package de.zeus.client.netty.pipelines;

import de.zeus.client.packet.Packet;
import de.zeus.client.utils.PacketBuffer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class PacketEncoder extends MessageToByteEncoder<Packet> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Packet packet, ByteBuf byteBuf) throws Exception {
        PacketBuffer packetBuffer = new PacketBuffer(byteBuf);
        int id = packet.getID();

        packetBuffer.writeVarIntToBuffer(id);
        packet.write(packetBuffer);
    }
}
