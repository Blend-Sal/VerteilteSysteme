package actor;

import fpinjava.Result;
import inout.Input;
import stream.Stream;


public class AskStream {

    public static Stream<String> ask(Actor<String> actor, String message, long timeout) {
        ActorReaderWriter actorreadwrite = ActorReaderWriter.actorReaderWriter("1", actor, timeout);
        actorreadwrite.print(message);
        return actorreadwrite.readLines();
    }

    public static Stream<String> ask(Writer transceiver, String message, long timeout) {
        ActorReaderWriter actorreadwrite = ActorReaderWriter.actorReaderWriter("1", transceiver, timeout);
        transceiver.start(Result.of(actorreadwrite));
        transceiver.tell(message, actorreadwrite);
        return actorreadwrite.readLines();
    }


}
