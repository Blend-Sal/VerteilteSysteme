package daytime.actor;

import actor.AbstractActor;
import actor.Actor;
import fpinjava.Result;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static java.time.LocalTime.now;


public class DaytimeActor extends AbstractActor<String> {


    public DaytimeActor(String id, Type type) {
        super(id, type);
    }

    @Override
    public void onReceive(String message, Result<Actor<String>> sender) {
        sender.forEach(x -> x.tell(now().format(DateTimeFormatter.ofPattern("HH:mm")),sender));

    }
}
