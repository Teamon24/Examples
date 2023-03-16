package patterns.basic.monad.ex1;

import lombok.AllArgsConstructor;

import java.util.Objects;
import java.util.function.Function;

@AllArgsConstructor
public class Failure<T> extends Try<T> {
    private final Throwable e;

    @Override
    public T get() throws Throwable {
        throw e;
    }

    @Override
    public T orElse(T value) {
        return value;
    }

    public <U> Failure<U> flatMap(Function<? super T, Try<U>> f) {
        Objects.requireNonNull(f);
        return failure(e);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Failure)) return false;
        Failure<?> failure = (Failure<?>) o;
        return Objects.equals(e, failure.e);
    }

    @Override
    public int hashCode() {
        return Objects.hash(e);
    }
}
