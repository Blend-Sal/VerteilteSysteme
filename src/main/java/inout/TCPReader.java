package inout;


import fpinjava.Callable;
import fpinjava.Result;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;


public class TCPReader extends AbstractReader {

    private final Socket socket;

    private TCPReader(BufferedReader reader, Socket socket) {
        super(reader);
        this.socket = socket;
    }

    private TCPReader(Socket socket) throws IOException {
        super(new BufferedReader(new InputStreamReader(socket.getInputStream())));
        this.socket = socket;

    }


    public static Callable<Input> accept(int localPort) {
        return () -> {
            try (ServerSocket server = new ServerSocket(localPort)) {
                Socket socket = server.accept();
                socket.shutdownOutput();
                return new TCPReader(socket);
            }
        };
    }

    public static Callable<Input> connectTo(String remoteHost, int remotePort) {
        return () -> {
            Socket socket = new Socket(remoteHost, remotePort);
            socket.shutdownOutput();
            return new TCPReader(socket);
        };
    }

    @Override
    public void shutdownInput() {
        try {
            socket.shutdownInput();
        } catch (IOException e) {
            System.err.println(e);
        }

    }
}
