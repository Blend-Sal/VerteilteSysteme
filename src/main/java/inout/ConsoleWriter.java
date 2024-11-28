package inout;

import java.io.PrintWriter;

public class ConsoleWriter extends AbstractWriter {

    private ConsoleWriter(PrintWriter writer) {
        super(writer);
    }

    public static ConsoleWriter stdout() {
        return new ConsoleWriter(new PrintWriter(System.out));
    }
}
