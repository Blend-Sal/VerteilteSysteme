package inout;

import java.io.OutputStream;
import java.io.PrintWriter;

public class AbstractWriter implements Output {

    private final PrintWriter writer;

    public AbstractWriter(PrintWriter writer) {
        this.writer = writer;
    }

    public AbstractWriter(OutputStream out) {
        this.writer = new PrintWriter(out);
    }

    @Override
    public void print(String s) {
        writer.print(s);
        writer.flush();

    }

    @Override
    public void shutdownOutput() {


    }
}
