package actor;

import fpinjava.Result;
import inout.Input;
import inout.Output;

import java.util.Objects;

public class Writer extends AbstractActor<String> {

    private final boolean transceiver;
    private final Input inp;

    private final Output out;

    public Writer(String id, Type type, boolean transceiver, Input inp, Output out) {
        super(id, type);
        this.transceiver = transceiver;
        this.inp = inp;
        this.out = out;
    }

    @Override
    public void onReceive(String message, Result<Actor<String>> sender) {
        if (Objects.equals(message, EOT)) {
            if (!transceiver) {
                out.printLine(EOT);
            }
            out.shutdownOutput();
            shutdown();
        } else {
            out.printLine(message);
        }
    }




    public void start() {
        final Reader read = new Reader("0001", Type.SERIAL, inp, transceiver, this);
        read.tell("", self());
    }

    public void start(Result<Actor<String>> consumer) {
        final Reader read = new Reader("0001", Type.SERIAL, inp, transceiver, this);
        read.tell("", consumer);
    }
}