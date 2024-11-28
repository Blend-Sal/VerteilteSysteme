package inout;

import fpinjava.Result;
import list.List;
import tuple.Tuple;

public class ScriptReader implements Input {
    private final List<String> commands;

    public ScriptReader(List<String> commands) {
        super();
        this.commands = commands;
    }

    public ScriptReader(String... commands) {
        super();
        this.commands = List.list(commands);
    }

    public Result<Tuple<String, Input>> readLine() {
        return commands.isEmpty()
                ? Result.failure("Not enough entries in script")
                : Result.success(new Tuple<>(commands.headOption().getOrElse(""),
                new ScriptReader(commands.drop(1))));
    }


    public List<String> toList() {
        return commands;
    }

    @Override
    public void shutdownInput() {

    }
}