package echo.tcpsocket;

import inout.Input;
import inout.InputOutput;
import inout.TCPReaderWriter;

import java.io.*;
import java.net.Socket;

import static java.lang.Integer.parseInt;

public class EchoClient {
    public static void main(String[] args) throws Exception {
        if (args.length != 3) {
            System.err.println("Usage: java EchoClient <host> <port> <message>");
            return;
        }

        String host = args[0];
        int port = parseInt(args[1]);
        InputOutput inout = TCPReaderWriter.connectTo(host, port).call();
        inout.printLine(args[2]);

        String line;
        while ((line = inout.readLine().successValue().fst) != null) {
            System.out.println(line);
        }
    }
}