package inout;

import fpinjava.Result;
import tuple.Tuple;

public class ProcessReaderWriter implements InputOutput {

    private final Process process;
    private final ProcessReader read;
    private final ProcessWriter write;


    private ProcessReaderWriter(Process process) {
        this.process = process;
        this.read = new ProcessReader(process);
        this.write = new ProcessWriter(process);
    }

    public static InputOutput processReaderWriter(Process process) {
        return new ProcessReaderWriter(process);
    }

    public Result<Tuple<String, Input>> readLine() {
        return read.readLine();
    }

    @Override
    public void shutdownInput() {
        read.shutdownInput();

    }

    @Override
    public void print(String s) {
        write.print(s);

    }

    @Override
    public void shutdownOutput() {
        write.shutdownOutput();

    }
}
