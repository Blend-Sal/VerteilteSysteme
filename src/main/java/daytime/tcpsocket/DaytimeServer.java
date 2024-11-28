package daytime.tcpsocket;

import inout.Output;
import inout.TCPWriter;

import java.time.format.DateTimeFormatter;
import static java.time.LocalDateTime.now;

public class DaytimeServer {
    private static void daytimese(Output out) {
        System.out.println("Verbindung hergestellt");
        out.printLine(now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        System.out.println("Verbindung beenden");
    }
    public static void main(String[] args) throws Exception {
        Output out = TCPWriter.accept(8).call();
        daytimese(out);
    }
}