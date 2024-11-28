package echo.actor;

import actor.AbstractActor;
import actor.Actor;
import fpinjava.Result;

public class EchoActor extends AbstractActor<String> {


    public EchoActor(String id, Type type) {
        super(id, type);
    }


    @Override
    public void onReceive(String message, Result<Actor<String>> sender) {
        sender.forEach(x -> {
            x.tell(message, sender);
        });


    }
}
