package echo.actor;

import actor.Actor;

import static actor.ActorSystem.publish2one;
import static java.lang.Integer.parseInt;

public class EchoServer {
    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            System.err.println("Usage: java EchoServer.jar -l <port>");
            return;
        }
        publish2one(new EchoActor("echoActor", Actor.Type.SERIAL), parseInt(args[1])).run();
    }
}