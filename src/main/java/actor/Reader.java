package actor;

import fpinjava.Result;
import inout.Input;

public class Reader extends AbstractActor<String> {

    private final Input inp;
    private final boolean transceiver;

    private final Actor<String> writer;

    public Reader(String id, Type type, Input inp, boolean transceiver, Actor<String> writer) {
        super(id, type);
        this.inp = inp;
        this.transceiver = transceiver;

        this.writer = writer;
    }

    @Override
    public void onReceive(String message, Result<Actor<String>> sender) {
        sender.forEach(x -> inp.readLines().forEach(y -> x.tell(y, writer)));
        if (!transceiver) {
            sender.forEach(x -> x.tell(EOT, writer));
            shutdown();
        }
    }

}
