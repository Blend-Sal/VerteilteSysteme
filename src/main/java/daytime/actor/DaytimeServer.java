package daytime.actor;

import actor.Actor;
import echo.actor.EchoActor;

import static actor.ActorSystem.publish2one;
import static java.lang.Integer.parseInt;

public class DaytimeServer {

    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            System.err.println("Usage: java DaytimeServer.jar -l <port>");
            return;
        }
        publish2one(new DaytimeActor("DaytimeActor", Actor.Type.SERIAL), parseInt(args[1])).run();
    }
}
