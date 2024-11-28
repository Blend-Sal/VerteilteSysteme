package actor;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class UserThreadFactory implements ThreadFactory{

    @Override
    public Thread newThread(Runnable runnableTask) {
        Thread thread = Executors.defaultThreadFactory().newThread(runnableTask);
        thread.setDaemon(false);
        return thread;
    }
}



