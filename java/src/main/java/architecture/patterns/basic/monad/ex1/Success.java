package architecture.patterns.basic.monad.ex1;

import lombok.AllArgsConstructor;

import java.util.Objects;
import java.util.function.Function;

@AllArgsConstructor
public class Success<T> extends Try<T> {

    private final T value;

    @Override
    public T get() {
        return value;
    }

    @Override
    public T orElse(T value) {
        return this.value;
    }

    public <U> Try<U> flatMap(Function<? super T, Try<U>> f) {
        Objects.requireNonNull(f);
        return f.apply(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Success)) return false;
        Success<?> success = (Success<?>) o;
        return Objects.equals(value, success.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
