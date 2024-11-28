package inout;

import actor.Actor;
import actor.Writer;
import fpinjava.Callable;
import fpinjava.Result;
import tuple.Tuple;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import static actor.Actor.Type.SERIAL;

public class TCPReaderWriter implements InputOutput {
    private final Socket socket;
    private final AbstractReader read;
    private final AbstractWriter write;

    public TCPReaderWriter(Socket socket, AbstractReader reader, AbstractWriter writer) {
        this.socket = socket;
        this.read = reader;
        this.write = writer;
    }

    public TCPReaderWriter(Socket socket) throws IOException {
        this.read = new AbstractReader(new BufferedReader(new InputStreamReader(socket.getInputStream())));
        this.write = new AbstractWriter(new PrintWriter(socket.getOutputStream()));
        this.socket = socket;
    }

    @Override
    public Result<Tuple<String, Input>> readLine() {
        return read.readLine();
    }

    @Override
    public void print(String s) {
        write.print(s);
    }

    @Override
    public void printLine(String s) {
        write.printLine(s);
    }

    @Override
    public void shutdownOutput() {
        try {
            socket.shutdownOutput();
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    @Override
    public void shutdownInput() {
        try {
            socket.shutdownInput();
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    public static Callable<InputOutput> accept(int localPort) {
        return () -> {
            ServerSocket server = new ServerSocket(localPort);
            Socket socket = server.accept();
            return new TCPReaderWriter(socket);
        };
    }

    public static Callable<InputOutput> connectTo(String remoteHost, int remotePort) {
        return () -> {
            Socket socket = new Socket(remoteHost, remotePort);
            return new TCPReaderWriter(socket);
        };
    }


}