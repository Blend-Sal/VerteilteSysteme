package echo.runnable;


import actor.Writer;
import inout.Input;
import inout.Output;

import static inout.ConsoleReader.stdin;
import static inout.ConsoleWriter.stdout;

public class Input2Output {

    static Runnable input2output(Input in, Output out) {
        return () -> {
            in.readLines().forEach(out::printLine);
            out.shutdownOutput();
        };
    }
    public static void main(String[] args) {
        input2output(stdin(), stdout()).run();

    }

}
