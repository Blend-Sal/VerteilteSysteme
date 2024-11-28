package actor;

import fpinjava.Result;
import inout.Input;
import tuple.Tuple;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

public class ActorReader extends AbstractActor<String> implements Input {

    private final long timeout;

    private final ArrayBlockingQueue<String> array = new ArrayBlockingQueue<String>(20);

    static Input actorReader(String id, long timeout) {
        return new ActorReader(id, Type.SERIAL, timeout);
    }

    public ActorReader(String id, Type type, long timeout) {
        super(id, type);
        this.timeout = timeout;
    }

    @Override
    public void onReceive(String message, Result<Actor<String>> sender) {
        array.offer(message);
    }

    @Override
    public Result<Tuple<String, Input>> readLine() {
        try {
            return Result.of(Tuple.tuple(array.poll(timeout, TimeUnit.MILLISECONDS), this));
        } catch (InterruptedException e) {
            return Result.failure(e);
        }
    }

    @Override
    public void shutdownInput() {


    }
}
