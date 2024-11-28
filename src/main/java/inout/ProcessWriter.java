package inout;

import java.io.*;

public class ProcessWriter extends AbstractWriter {
    private final Process process;

    public ProcessWriter(Process process) {
        super(new PrintWriter(process.outputWriter(), true));
        this.process = process;
    }

    public static Output processWriter(Process process) {
        return new ProcessWriter(process);

    }
}


