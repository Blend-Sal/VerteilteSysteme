package inout;

import java.io.BufferedReader;
import java.io.InputStream;

public class ProcessReader extends AbstractReader{
    private final Process process;


    public ProcessReader(Process process) {
        super(process.inputReader());
        this.process = process;
    }


    public static Input processReader(Process process){
        return new ProcessReader(process);

    }


}

