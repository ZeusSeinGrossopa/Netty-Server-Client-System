import de.zeus.client.NettyClient;
import de.zeus.client.packet.packets.PacketMessage;

public class RunClient {

    public static void main(String[] args) {
        NettyClient nettyClient = new NettyClient();
        nettyClient.connect("127.0.0.1", 8080);

        nettyClient.sendPacket(new PacketMessage("Hello Server!"));
    }
}