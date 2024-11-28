package echo.tcpsocket;

import inout.Output;
import inout.TCPReaderWriter;
import inout.TCPWriter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {
    public static void main(String[] args) throws Exception {
        final var readwrite = TCPReaderWriter.accept(8).call();
        readwrite.readLines().forEach(readwrite::printLine);

    }

}
