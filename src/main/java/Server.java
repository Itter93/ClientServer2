import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;


public class Server {

    public static void main(String[] args) throws IOException {
        final ServerSocketChannel serverChannel = ServerSocketChannel.open();
        serverChannel.bind(new InetSocketAddress("localhost", 23334));

        while (true) {
            try (SocketChannel socketChannel = serverChannel.accept()) {
                final ByteBuffer input = ByteBuffer.allocate(2 << 10);
                while (socketChannel.isConnected()) {
                    int bytesCounts = socketChannel.read(input);
                    if (bytesCounts == -1) break;
                    final String msg = new String(input.array(), 0, bytesCounts, StandardCharsets.UTF_8);
                    input.clear();
                    String withoutSpaces= msg.replaceAll(" ", "");
                    socketChannel.write(ByteBuffer.wrap(("Сообщение от клиета без пробелов: " + withoutSpaces).getBytes(StandardCharsets.UTF_8)));
                }

            }
        }
    }
}



