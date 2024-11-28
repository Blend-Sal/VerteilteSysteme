package inout;

import fpinjava.Result;
import stream.Stream;
import tuple.Tuple;

public interface Input {

    Result<Tuple<String, Input>> readLine();

    default Result<Tuple<Integer, Input>> readInt() {
        return readLine().flatMap(x -> readIntMaybe(x.fst).flatMap(y -> Result.of(new Tuple<Integer, Input>(y, x.snd))));
    }

    static Result<Integer> readIntMaybe(String s) {
        return Result.of(() -> Integer.parseInt(s));
    }

    default Stream<String> readLines() {
        return Stream.<String, Input>unfold(this, Input::readLine);
    }

    default Stream<Integer> readInts() {
        return Stream.<Integer, Input>unfold(this, Input::readInt);
    }

    void shutdownInput();


}
