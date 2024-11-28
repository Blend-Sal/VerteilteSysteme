package daytime.tcp;

import java.io.InputStream;
import java.net.Socket;

public class DaytimeClient {
    public static void main(String[] args) {
        try (
                Socket socket = new Socket("localhost", 9999);
                InputStream in = socket.getInputStream();
        ) {
            StringBuilder time = new StringBuilder();
            int c;
            while ((c = in.read()) != -1) {
                time.append((char) c);
            }
            System.out.println(time);
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}