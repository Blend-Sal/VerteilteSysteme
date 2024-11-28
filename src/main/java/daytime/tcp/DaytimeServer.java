package daytime.tcp;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.format.DateTimeFormatter;

import static java.time.LocalDateTime.now;


public class DaytimeServer {

    private static void daytimese(PrintWriter out) throws IOException {
        out.println(now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd[ HH:mm:ss]")));

    }
    public static void main(String[] args) {
        try (
                ServerSocket serverSocket = new ServerSocket(9999);
                Socket socket = serverSocket.accept();
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true)

        ) { System.out.println("Verbindung hergestellt");
            daytimese(out);
            System.out.println("Verbindung beenden");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

