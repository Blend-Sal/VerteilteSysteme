package echo.tcp;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import static java.lang.Integer.parseInt;

public class EchoClient {
    public static void main(String[] args) {

        try (
                Socket socket = new Socket(args[0], parseInt(args[1]));) {

            OutputStream out = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(out, true);
            writer.println(args[2]);

            InputStream inp = socket.getInputStream();
            BufferedReader read = new BufferedReader(new InputStreamReader(inp));
            String line;
            while ((line = read.readLine()) != null) {
                System.out.println(line);
            }
            socket.close();
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}