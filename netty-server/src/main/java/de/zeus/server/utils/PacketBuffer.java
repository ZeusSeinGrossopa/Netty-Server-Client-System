package de.zeus.server.utils;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.SwappedByteBuf;

import java.nio.charset.StandardCharsets;

public class PacketBuffer extends SwappedByteBuf {

    public PacketBuffer(ByteBuf buf) {
        super(buf);
    }

    public static int getVarIntSize(int input) {
        for (int var1 = 1; var1 < 5; var1++) {
            if ((input & -1 << var1 * 7) == 0)
                return var1;
        }
        return 5;
    }

    public void writeString(String string) {
        if(string == null)
            string = "null";

        writeInt(string.getBytes(StandardCharsets.UTF_8).length);
        writeBytes(string.getBytes(StandardCharsets.UTF_8));
    }

    public String readString() {
        byte[] bytes = new byte[readInt()];

        for(int i = 0; i < bytes.length; i++)
            bytes[i] = readByte();

        return new String(bytes, StandardCharsets.UTF_8);
    }

    public void writeVarIntToBuffer(int input) {
        while ((input & 0xFFFFFF80) != 0) {
            writeByte(input & 0x7F | 0x80);
            input >>>= 7;
        }
        writeByte(input);
    }

    public int readVarIntFromBuffer() {
        byte var3;
        int var1 = 0;
        int var2 = 0;

        do {
            var3 = readByte();
            var1 |= (var3 & 0x7F) << var2++ * 7;
            if (var2 > 5)
                throw new RuntimeException("VarInt too big");
        } while ((var3 & 0x80) == 128);
        return var1;
    }
}