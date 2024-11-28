package echo.runnable;

import com.sun.tools.javac.Main;
import inout.ScriptReader;
import inout.ScriptWriter;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static echo.runnable.Input2Output.input2output;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Input2OutputTest {

    @ParameterizedTest
    @CsvSource({"'Hallo1\nHallo2'",
            "'HalloHallo\njaja'"})
    public void test(String s) {
        ScriptReader read = new ScriptReader(s);
        ScriptWriter write = new ScriptWriter();
        Runnable run = input2output(read, write);
        run.run();
        assertEquals(read.toList(), write.toList());


    }
}
