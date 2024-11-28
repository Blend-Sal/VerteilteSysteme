package actor;

import fpinjava.Result;
import inout.Input;
import inout.InputOutput;
import tuple.Tuple;

public class ActorReaderWriter extends AbstractActor<String> implements InputOutput {
    private final ActorReader actorReader;
    private final Actor<String> actortotest;

    public ActorReaderWriter(String id, Type type, ActorReader actorReader, Actor<String> actortotest) {
        super(id, type);
        this.actorReader = actorReader;
        this.actortotest = actortotest;
    }

    @Override
    public void onReceive(String message, Result<Actor<String>> sender) {
        actorReader.onReceive(message, sender);
    }

    @Override
    public Result<Tuple<String, Input>> readLine() {
        return actorReader.readLine();
    }

    @Override
    public void shutdownInput() {
    }

    @Override
    public void print(String s) {
        actortotest.tell(s, this);
    }

    @Override
    public void shutdownOutput() {
    }

    static ActorReaderWriter actorReaderWriter(String id, Actor<String> actor, long timeout) {
        return new ActorReaderWriter(id, Type.SERIAL, new ActorReader(id, Type.SERIAL, timeout), actor);

    }
}
