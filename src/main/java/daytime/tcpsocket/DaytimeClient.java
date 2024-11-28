package daytime.tcpsocket;
import inout.Input;
import inout.TCPReader;
import static java.lang.Integer.parseInt;

public class DaytimeClient {

    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            System.err.println("Usage: java DaytimeClient <host> <port>");
            return;
        }

        String host = args[0];
        int port = parseInt(args[1]);
        Input in = TCPReader.connectTo(host, port).call();
        in.readLines().forEach(System.out::println);
    }
}