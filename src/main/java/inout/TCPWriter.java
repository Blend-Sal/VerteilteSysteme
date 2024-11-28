package inout;

import fpinjava.Callable;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPWriter extends AbstractWriter {
    private final Socket socket;
    TCPWriter(PrintWriter writer, Socket socket) {
        super(writer);
        this.socket = socket;
    }

    public TCPWriter(Socket socket) throws IOException {
        super(new PrintWriter(socket.getOutputStream()));
        this.socket = socket;
    }

    public static Callable<Output> accept(int localPort) {
        return () -> {
            try (ServerSocket server = new ServerSocket(localPort)) {
                Socket socket = server.accept();
                socket.shutdownInput();
                return new TCPWriter(socket);
            }
        };
    }
    public static Callable<Output> connectTo(String remoteHost, int remotePort) {
        return () -> {
            Socket socket = new Socket(remoteHost, remotePort);
            socket.shutdownInput();
            return new TCPWriter(socket);
        };
    }

    @Override
    public void shutdownOutput() {
        try {
            socket.shutdownOutput();
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}