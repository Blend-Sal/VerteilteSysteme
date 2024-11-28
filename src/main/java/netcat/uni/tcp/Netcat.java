package netcat.uni.tcp;

import actor.Actor;
import actor.Writer;
import inout.TCPReader;
import inout.TCPWriter;

import java.util.Objects;

import static inout.ConsoleReader.stdin;
import static inout.ConsoleWriter.stdout;
import static java.lang.Integer.parseInt;

public class Netcat {

    public static void netcatServer(int port) throws Exception {
        Writer servWrite = new Writer("servWrite", Actor.Type.SERIAL, false, TCPReader.accept(port).call(), stdout());
        servWrite.start();

    }

    public static void netcatClient(String host, int port) throws Exception {
        Writer clientWrite = new Writer("clientwrite", Actor.Type.SERIAL, false, stdin(), TCPWriter.connectTo(host, port).call());
        clientWrite.start();

    }

    public static void main(String[] args) throws Exception {

        if (args.length != 2) {
            System.err.println("Usage client: Netcat <host> <port> \n usage server: Netcat -l <port>");
        } else {
            if (Objects.equals(args[0], "-l")) {
                netcatServer(parseInt(args[1]));
            } else {
                netcatClient(args[0], parseInt(args[1]));
            }
        }
    }
}
