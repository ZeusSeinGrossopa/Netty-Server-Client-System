import de.zeus.server.NettyServer;

public class RunServer {

    public static void main(String[] args) {
        NettyServer server = new NettyServer();

        server.start("127.0.0.1", 8080);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            server.stop();
            System.out.println("Server stopped!");
        }));
    }
}