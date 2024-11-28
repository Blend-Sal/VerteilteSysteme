package actor;

import fpinjava.Result;

public interface ActorContext<T> {
    void become(MessageProcessor<T> behavior);

    MessageProcessor<T> getBehavior();
}

