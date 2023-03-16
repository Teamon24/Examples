package patterns.basic.monad.ex1;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class Try<T> {

    public abstract T orElse(T value);

    public abstract T get() throws Throwable;

    public abstract <U> Try<U> flatMap(Function<? super T, Try<U>> f);

    public static <U> Success<U> successful(U u) {
        return new Success<>(u);
    }

    public static <U> Failure<U> failure(Throwable e) {
        return new Failure<>(e);
    }

    public static <U> Try<U> of(Supplier<U> f) {
        Objects.requireNonNull(f);
        try {
            U u = f.get();
            return Try.successful(u);
        } catch (Throwable e) {
            return Try.failure(e);
        }
    }
}
