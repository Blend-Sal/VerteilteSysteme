package echo.tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {
    public static void main(String[] args) {
        try (
                ServerSocket serverSocket = new ServerSocket(7777);
                Socket socket = serverSocket.accept();
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true)
        ) {
            System.out.println("Verbindung hergestellt");
            String input;
            while ((input = in.readLine()) != null) {
                out.println(input);
            }
            System.out.println("Verbindung beenden");
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}