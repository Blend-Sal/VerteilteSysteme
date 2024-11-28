package echo.actor;

import actor.Actor;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import stream.Stream;
//import static actor.AskWithOutput.ask;
import static actor.AskStream.ask;
import static org.junit.jupiter.api.Assertions.assertEquals;
//import static list.List.list;

public class EchoActorTest {

    @ParameterizedTest
    @CsvSource({"'Hallo'",
            "'HalloHallo'"})
    public void test(String s) throws InterruptedException {
        EchoActor echotest = new EchoActor("1", Actor.Type.SERIAL);
        Stream<String> stream = ask(echotest, s, 4000);
        assertEquals(s, stream.head());


    }
}
