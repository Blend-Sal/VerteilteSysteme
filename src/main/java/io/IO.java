package io;

import fpinjava.*;
import inout.Input;
import inout.Output;
import list.List;
import stream.Stream;
import tuple.Tuple;

import java.io.Console;

import static fpinjava.Result.maybe;
import static fpinjava.TailCall.ret;
import static fpinjava.TailCall.sus;

public abstract class IO<A> {

    protected abstract boolean isReturn();

    protected abstract boolean isSuspend();

    private static IO<Nothing> EMPTY = new IO.Suspend<>(() -> Nothing.instance);

    public static IO<Nothing> empty() {
        return EMPTY;
    }

    public A run() {
        return run(this);
    }

    private A run(IO<A> io) {
        return run_(io).eval();
    }

    private TailCall<A> run_(IO<A> io) {
        if (io.isReturn()) {
            return ret(((Return<A>) io).value);
        } else if (io.isSuspend()) {
            return ret(((Suspend<A>) io).resume.get());
        } else {
            Continue<A, A> ct = (Continue<A, A>) io;
            IO<A> sub = ct.sub;
            Function<A, IO<A>> f = ct.f;
            if (sub.isReturn()) {
                return sus(() -> run_(f.apply(((Return<A>) sub).value)));
            } else if (sub.isSuspend()) {
                return sus(() -> run_(f.apply(((Suspend<A>) sub).resume.get())));
            } else {
                Continue<A, A> ct2 = (Continue<A, A>) sub;
                IO<A> sub2 = ct2.sub;
                Function<A, IO<A>> f2 = ct2.f;
                return sus(() -> run_(sub2.flatMap(x -> f2.apply(x).flatMap(f))));
            }
        }
    }

    public <B> IO<B> map(Function<A, B> f) {
        return flatMap(f.andThen(Return::new));
    }

    public <B> IO<B> ap(IO<Function<A, B>> iof) {
        return iof.flatMap(f -> flatMap(x -> pure(() -> f.apply(x))));
    }

    public static <A> IO<A> pure(A a) {
        return unit(a);
    }

    public static <A> IO<A> pure(Supplier<A> a) {
        return unit(a);
    }

    @SuppressWarnings("unchecked")
    public <B> IO<B> flatMap(Function<A, IO<B>> f) {
        return (IO<B>) new Continue<>(this, f);
    }

    public <B> IO<B> bind(Function<A, IO<B>> f) {
        return flatMap(f);
    }

    public Runnable bindRunnable(Function<A, Runnable> f) {
        return () -> {
            try {
                f.apply(this.run()).run();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }

    public <B> IO<B> append(IO<B> iob) {
        return bind(ignore -> iob);
    }

    public static <A> IO<A> unit(A a) {
        return new IO.Suspend<>(() -> a);
    }

    public static <A> IO<A> unit(Supplier<A> a) {
        return new IO.Suspend<>(a);
    }

    public static IO<Nothing> fromRunnable(Runnable a) {
        return new IO.Suspend<>(() -> {
            a.run();
            return Nothing.instance;
        });
    }

    public static <A> IO<A> fromCallable(Callable<A> s) {
        return new IO.Suspend<>(() -> {
            final A value;
            try {
                value = s.call();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return value;
        });
    }

    public static <A, B, C> IO<C> mapM2(Function<A, Function<B, C>> f, IO<A> ioa, IO<B> iob) {
        return liftM2(f).apply(ioa).apply(iob);
    }

    public static <A, B, C> Function<IO<A>, Function<IO<B>, IO<C>>> liftM2(final Function<A, Function<B, C>> f) {
        return a -> b -> b.ap(a.map(f));
    }

    public static <A, B, C> Function<IO<A>, Function<Supplier<IO<B>>, IO<C>>>
    liftA2(final Function<A, Function<B, C>> f) {
        return a -> sb -> sb.map(b -> b.ap(a.map(f))).get();
    }

    public static <A> IO<Nothing> doWhile(IO<A> iot, Function<A, IO<Boolean>> f) {
        return iot.flatMap(f::apply)
                .flatMap(ok -> ok
                        ? doWhile(iot, f)
                        : EMPTY);
    }
  /*
  public static <A> IO<Nothing> repeat(int n, IO<A> io) {
    return forEach(Stream.fill(n, () -> io), IO::skip);
  }

   */

    public static <A, B> IO<B> forever(IO<A> ioa) {
        Supplier<IO<B>> a = () -> forever(ioa);
        return ioa.flatMap(x -> a.get());
    }
  /*
  public static <A,B> IO<B> foldlM(Function<B, Function<A, IO<B>>> f, B z, Stream<A> s) {
    return s.isEmpty()
            ? unit(z)
            : f.apply(z).apply(s.head().fst).flatMap(zz -> foldlM(f, zz, s.tail()));
  }

   */

    static <A, B> IO<B> as(IO<A> a, B b) {
        return a.map(ignore -> b);
    }

    static <A> IO<Nothing> skip(IO<A> a) {
        return as(a, Nothing.instance);
    }
    /*
  public static <A, B> IO<Nothing> foldlM_(Function<B, Function<A, IO<B>>> f, B z, Stream<A> s) {
    return skip(foldlM(f, z, s));
  }

     */
    /*
    public static <A> IO<Nothing> forEach(Stream<A> s, Function<A, IO<Nothing>> f) {
        return foldlM_(n -> a -> skip(f.apply(a)), Nothing.instance, s);
    }

    public static <A> IO<Nothing> sequence_(Stream<IO<A>> stream) {
        return forEach(stream, IO::skip);
    }

    public static <A> IO<Nothing> sequence_(List<IO<A>> list) {
        return sequence_(Stream.of(list));
    }

    @SafeVarargs
    public static <A> IO<Nothing> sequence_(IO<A>... array) {
        return sequence_(Stream.of(array));
    }

    public static <A> IO<Boolean> when(boolean b, Supplier<IO<A>> sIoa) {
        return b
                ? as(sIoa.get(), true)
                : unit(false);
    }

     */
/*
    @SafeVarargs
    public static <A> IO<Stream<A>> sequence(IO<A>... array) {
        return sequence(Stream.of(array));
    }

    public static <A> IO<Stream<A>> sequence(List<IO<A>> list) {
        return sequence(Stream.of(list));
    }

    public static <A> IO<Stream<A>> sequence(Stream<IO<A>> fs) {
        return fs.foldr(
                f -> acc -> mapM2(a -> b -> cons(() -> a, () -> b), f, acc.get()),
                () -> unit(Stream::empty)
        );
    }

    public static <A, B> IO<Stream<B>> mapM(Function<A, IO<B>> f, Stream<A> s) {
        return sequenceA(s.map(f));
    }

    public static <A, B> IO<Stream<B>> mapM(Function<A, IO<B>> f, List<A> s) {
        return mapM(f, Stream.of(s));
    }

    public static <A> IO<Stream<A>> sequenceA(Stream<IO<A>> fs) {
        return fs.foldr(
                liftA2(a -> b -> cons(() -> a, () -> b)),
                () -> pure(Stream.empty())
        );
    }

    static <A, B> IO<B> foldr(Function<IO<A>, Function<IO<B>, IO<B>>> f, IO<B> s, Stream<IO<A>> xs) {
        return xs.isEmpty() ? s
                : f.apply(xs.head().fst).apply(foldr(f, s, xs.tail()));
    }

    /************************ Input Functions *******************************/
    /*

    public static Input stdin() {
        return Console.stdin();
    }

    public static IO<Boolean> fEOF(Input in) {
        return pure(in.isEOF());
    }

    public static IO<String> hGetLine(Input in) {
        return pure(() -> {
            try {
                return in.readLine().map(Tuple::fst).successValue();
            } catch (IllegalStateException e) {
                throw new IllegalStateException("hGetLine: isEOFError!");
            }
        });
    }

    public static IO<Result<String>> hGetLineMaybe(Input in) {
        return pure(() -> Result.of(() -> in.readLine().map(Tuple::fst).successValue()));
    }

    public static IO<String> getLine() {
        return hGetLine(stdin());
    }

    public static IO<Stream<String>> hGetLines(Input in) {
        return new Suspend<>(in::readLines);
    }
    /*
    public static IO<Stream<String>> hGetLinesLazy(Input in) {
        return new Suspend<>(in::readLnsLazy);
    }



    public static IO<Stream<String>> getLines() {
        return hGetLines(stdin());
    }

    /************************* Output Functions ******************************/
    /*
    public static Output stdout() {
        return Console.stdout();
    }


    public static IO<Nothing> hClose(Output out) {
        return new Suspend<>(() -> {
            out.shutdownOutput();
            return Nothing.instance;
        });
    }

    public static IO<Nothing> hPutStr(Output out, String s) {
        return new Suspend<>(() -> {
            out.print(s);
            return Nothing.instance;
        });
    }

    public static IO<Nothing> putStr(String s) {
        return hPutStr(stdout(), s);
    }

    public static IO<Nothing> hPutStrLn(Output out, String s) {
        return new Suspend<>(() -> {
            out.printLine(s);
            return Nothing.instance;
        });
    }

    public static IO<Nothing> putStrLn(String s) {
        return hPutStrLn(stdout(), s);
    }

    public static IO<Nothing> hPutStrLns_(Output out, Stream<String> ss) {
        return forEach(ss, s -> hPutStrLn(out, s));
    }

    public static IO<Nothing> putStrLns_(Stream<String> ss) {
        return hPutStrLns_(stdout(), ss);
    }
    /*
    public static IO<Stream<Nothing>> hPutStrLns(Output out, Stream<String> ss) {
        return sequenceA(ss.map(s -> hPutStrLn(out, s)));
    }

    public static IO<Stream<Nothing>> putStrLns(Stream<String> ss) {
        return hPutStrLns(stdout(), ss);
    }

    /*********************** InputOutput Functions *****************************/
/*
    public static IO<String> hPromptLine(Output out, Input in, String req) {
        return hPutStrLn(out, req).append(hGetLine(in)); // nicht getestet
    }
    /*
    public static IO<String> hPromptLine(InputOutput inout, String req) {
        return hPromptLine(inout, inout, req);
    }



    public static IO<String> promptLine(String req) {
        return hPromptLine(stdout(), stdin(), req);
    }

    public static IO<Stream<String>> hPromptLines(Output out, Input in, String req) {
        // return hPutStrLn(out,req).flatMap(x -> hGetLines(in)); //getestet
        return hPutStrLn(out, req).append(hGetLines(in));//nicht getestet!
    }
    /*
    public static IO<Stream<String>> hPromptLines(InputOutput inout, String req) {
        return hPromptLines(inout, inout, req);
    }



    public static IO<Stream<String>> promptLines(String req) {
        return hPromptLines(stdout(), stdin(), req);
    }

    // nicht stack safe!
    public static IO<Stream<String>> hPromptLineByLine(Output out, Input in, Stream<String> reqs) {
        return mapM(r -> hPromptLine(out, in, r), reqs);
    }

    // nicht stack safe!
    /*
    public static IO<Stream<String>> hPromptLineByLine(InputOutput inout, Stream<String> reqs) {
        return hPromptLineByLine(inout, inout, reqs);
    }

    public static IO<Stream<String>> hPromptLineByLine(InputOutput inout, String... reqs) {
        return hPromptLineByLine(inout, inout, stream(reqs));
    }

    public static IO<Nothing> hLineInteract(Input in, Output out, Function<String, String> f) {
        return hGetLines(in).map(l -> l.map(f)).flatMap(l -> hPutStrLns_(out, l));
    }

     */
/*
    public static <S> IO<Nothing> hLineInteractWith(Input in, Output out,
                                                    S state,
                                                    Function<S, Function<String, Result<Tuple<String, S>>>> f) {
        return hGetLineMaybe(in).bind(rx ->
                maybe(pure(Nothing.instance)
                        , x -> {
                            Result<Tuple<String, S>> y = f.apply(state).apply(x);
                            return maybe(pure(Nothing.instance)
                                    , t -> hPutStr(out, y.successValue().fst).append(
                                            hLineInteractWith(in, out, y.successValue().snd, f))
                                    , y);
                        }
                        , rx)
        );
    }

    public static <S> IO<Nothing> lineInteractWith(S state,
                                                   Function<S, Function<String, Result<Tuple<String, S>>>> f) {
        return hLineInteractWith(stdin(), stdout(), state, f);
    }
    /*
    public static IO<Nothing> hLineInteract(InputOutput inout, Function<String, String> f) {
        return hLineInteract(inout, inout, f);
    }

     */
/*
    public static IO<Nothing> lineInteract(Function<String, String> f) {
        return hLineInteract(stdin(), stdout(), f);
    }

    public static IO<Nothing> hLineCopy(Input in, Output out) {
        return hLineInteract(in, out, Function.identity());
    }
    /*
    public static IO<Nothing> hLineEcho(InputOutput inout) {
        return hLineCopy(inout, inout);
    }


    public static IO<Nothing> lineEcho() {
        return hLineInteract(stdin(), stdout(), Function.identity());
    }
    */

    static <A> IO<IORef<A>> ref(A a) {
        return unit(new IORef<>(a));
    }


    final static class IORef<A> {
        private A value;

        public IORef(A a) {
            this.value = a;
        }

        public IO<A> set(A a) {
            value = a;
            return unit(a);
        }

        public IO<A> get() {
            return unit(value);
        }

        public IO<A> modify(Function<A, A> f) {
            return get().flatMap(a -> set(f.apply(a)));
        }
    }

    final static class Return<A> extends IO<A> {
        public final A value;

        protected Return(A value) {
            this.value = value;
        }

        @Override
        public boolean isReturn() {
            return true;
        }

        @Override
        public boolean isSuspend() {
            return false;
        }
    }

    final static class Suspend<A> extends IO<A> {
        public final Supplier<A> resume;

        protected Suspend(Supplier<A> resume) {
            this.resume = resume;
        }

        @Override
        public boolean isReturn() {
            return false;
        }

        @Override
        public boolean isSuspend() {
            return true;
        }
    }

    final static class Continue<A, B> extends IO<A> {
        public final IO<A> sub;
        public final Function<A, IO<B>> f;

        protected Continue(IO<A> sub, Function<A, IO<B>> f) {
            this.sub = sub;
            this.f = f;
        }

        @Override
        public boolean isReturn() {
            return false;
        }

        @Override
        public boolean isSuspend() {
            return false;
        }
    }
}
