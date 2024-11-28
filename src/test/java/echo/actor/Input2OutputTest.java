package echo.actor;

import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.assertEquals;

import actor.Actor;
import actor.Writer;
import inout.ScriptReader;
import inout.ScriptWriter;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public  class Input2OutputTest {

	@ParameterizedTest
	@CsvSource({"'Hallo'",
							"'HalloHallo'"})
	public void test(String s) throws InterruptedException {
        ScriptReader read = new ScriptReader(s);
        ScriptWriter write = new ScriptWriter();
        Writer writer = new Writer("0002", Actor.Type.SERIAL, true, read, write);
        writer.start();
        sleep(4000);
        assertEquals(read.toList(), write.toList());

  }
}
