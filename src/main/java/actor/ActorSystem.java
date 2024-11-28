package actor;

import fpinjava.Callable;
import fpinjava.Result;
import inout.InputOutput;
import inout.TCPReaderWriter;

import java.net.ServerSocket;

public class ActorSystem {

    public static Runnable publish2one(Actor<String> actor, int port) {
        return () -> {
            try {
                final InputOutput inout = TCPReaderWriter.accept(port).call();
                Writer write = new Writer("writer", Actor.Type.SERIAL, true, inout, inout);
                write.start(Result.of(actor));
            } catch (Exception e) {
                System.err.println(e);
            }
        };
    }

    public static Callable<Writer> actorSelection(String host, int port) {
        return () -> {
            final InputOutput inout = TCPReaderWriter.connectTo(host, port).call();
            return new Writer("writer", Actor.Type.SERIAL, true, inout, inout);

        };
    }

    public static Runnable publish2multiple(Actor<String> actor, int port) throws Exception {
        return () -> {
            try (ServerSocket socket = new ServerSocket(port)) {
                while (true) {
                    InputOutput inout = new TCPReaderWriter(socket.accept());
                    Writer w = new Writer("publish2multiple", Actor.Type.SERIAL, true, inout, inout);
                    w.start(Result.of(actor));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
    }


}

