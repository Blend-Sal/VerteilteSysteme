package inout;

import fpinjava.Result;
import tuple.Tuple;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class AbstractReader implements Input {
    protected final BufferedReader reader;

    protected AbstractReader(BufferedReader reader) {
        this.reader = reader;
    }

    public AbstractReader(InputStream in) {
        this.reader = new BufferedReader(new InputStreamReader(in));
    }

    @Override
    public Result<Tuple<String, Input>> readLine() {
        try {
            String s = reader.readLine();
            return s == null
                    ? Result.empty()
                    : Result.success(new Tuple<>(s, this));
        } catch (Exception e) {
            return Result.failure(e);
        }
    }


    @Override
    public void shutdownInput() {
    }

}