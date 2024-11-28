package DaytimeActorTest;


import actor.Actor;
import daytime.actor.DaytimeActor;
import stream.Stream;


import static actor.AskStream.ask;
import static java.lang.Thread.sleep;

public class DaytimeActorTest {

    public static void main(String[] args) throws InterruptedException {
        DaytimeActor daytime = new DaytimeActor("1", Actor.Type.SERIAL);
        Stream<String> tester = ask(daytime, "Hallo", 2000);
        sleep(4000);
        System.out.println(tester.head());
    }

}
