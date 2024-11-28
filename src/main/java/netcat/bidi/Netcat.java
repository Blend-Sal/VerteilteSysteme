package netcat.bidi;

import actor.Actor;
import actor.Writer;
import fpinjava.Result;
import inout.InputOutput;
import inout.TCPReaderWriter;

import java.util.Objects;

import static inout.ConsoleReader.stdin;
import static inout.ConsoleWriter.stdout;
import static java.lang.Integer.parseInt;
import static java.lang.Thread.sleep;

public class Netcat {

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

    private static void netcatServer(int port) throws Exception {
        final InputOutput servinpout = TCPReaderWriter.accept(port).call();
        Writer serverSockW = new Writer("serverSockW", Actor.Type.SERIAL, false, servinpout, servinpout);
        Writer serverConsW = new Writer("serverConsW", Actor.Type.SERIAL, false, stdin(), stdout());
        serverSockW.start(Result.of(serverConsW));
        serverConsW.start(Result.of(serverSockW));



    }

    private static void netcatClient(String host, int port) throws Exception {
        final InputOutput clientinpout = TCPReaderWriter.connectTo(host, port).call();
        Writer clientsockW = new Writer("sockW", Actor.Type.SERIAL, false, clientinpout, clientinpout);
        Writer clientconsW = new Writer("consW", Actor.Type.SERIAL, false, stdin(), stdout());
        clientsockW.start(Result.of(clientconsW));
        clientconsW.start(Result.of(clientsockW));


    }

}
