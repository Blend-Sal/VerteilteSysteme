package stream;

import fpinjava.Effect;
import fpinjava.Function;
import fpinjava.Result;
import fpinjava.Supplier;
import inout.Input;
import tuple.Tuple;

public abstract class Stream<A> {
    private static Stream EMPTY = new Empty();

    public static <A, S> Stream<A> unfold(S z,
                                          Function<S, Result<Tuple<A, S>>> f) {
        return f.apply(z).map(x -> cons(() -> x.fst,
                () -> unfold(x.snd, f))).getOrElse(empty());
    }

    public abstract A head();

    public abstract Stream<A> tail();

    public abstract Boolean isEmpty();

    public abstract void forEach(Effect<A> ef);


    public Result<A> headOption() {
        return null;
    }

    private Stream() {
    }

    private static class Empty<A> extends Stream<A> {
        @Override
        public Stream<A> tail() {
            throw new IllegalStateException("tail called on empty");
        }

        @Override
        public A head() {
            throw new IllegalStateException("head called on empty");
        }

        @Override
        public Boolean isEmpty() {
            return true;
        }

        @Override
        public void forEach(Effect<A> ef) {

        }


        public Result<A> headOption() {
            return Result.empty();
        }
    }

    private static class Cons<A> extends Stream<A> {
        private final Supplier<A> head;
        private final Supplier<Stream<A>> tail;

        private Cons(Supplier<A> h, Supplier<Stream<A>> t) {
            head = h;
            tail = t;
        }

        @Override
        public A head() {
            return head.get();
        }

        @Override
        public Stream<A> tail() {
            return tail.get();
        }

        @Override
        public Boolean isEmpty() {
            return false;
        }

        @Override
        public void forEach(Effect<A> ef) {
            ef.apply(head());
            tail().forEach(ef);
        }


        public Result<A> headOption() {
            return Result.success(head());
        }
    }

    static <A> Stream<A> cons(Supplier<A> hd, Supplier<Stream<A>> tl) {
        return new Cons<>(hd, tl);
    }

    @SuppressWarnings("unchecked")
    public static <A> Stream<A> empty() {
        return EMPTY;
    }

    public static Stream<Integer> from(int i) {
        return cons(() -> i, () -> from(i + 1));
    }


    //Was war eigentlich nochmal Endrekursion?
    //Eine Methode wurde Endrekursiv gennant wenn der letzte aufruf der Methode die Methode selber war.
    //Ein Vorteil der Endrekursion ist das es zu keinem Stackoverflow kommen kann.

    /*
    Analysieren und erklären Sie die Methode readIntMaybe!
    Die Methode readIntMaybe wandelt einen gefunden int der sich in einem String befindet in einen int um, falls es leer ist wirft es ein Result Empty.
     */
}
