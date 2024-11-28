package echo.actor;

import actor.Actor;
import actor.Writer;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import stream.Stream;

import static actor.ActorSystem.*;
import static actor.AskStream.ask;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DistEchoActorTest {

    @ParameterizedTest
    @CsvSource({"'Hallo'"})
    public void test(String s) throws Exception {
        Thread echo = new Thread(publish2multiple(new EchoActor("id", Actor.Type.SERIAL), 1111));
        echo.start();
        Writer writer = actorSelection("localhost", 1111).call();
        assertEquals(ask(writer, s, 800).head(), s);

    }
}
