package daytime.actor;

import actor.Writer;

import static actor.ActorSystem.actorSelection;
import static actor.AskStream.ask;

public class DaytimeClient {

    public static void main(String[] args) throws Exception {
        if (args.length != 3) {
            System.err.println("Usage: java DaytimeClient.jar <host> <port> <message>");
            return;
        }
        Writer writer = actorSelection(args[0], Integer.parseInt(args[1])).call();
        ask(writer, args[2], 2000).forEach(System.out::println);

    }
}
