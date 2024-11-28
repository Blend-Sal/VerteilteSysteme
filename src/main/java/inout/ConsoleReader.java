package inout;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ConsoleReader extends AbstractReader {
    protected ConsoleReader(BufferedReader reader) {
        super(reader);
    }


    public static ConsoleReader stdin() {
        return new ConsoleReader(new BufferedReader(
                new InputStreamReader(System.in)));
    }

}