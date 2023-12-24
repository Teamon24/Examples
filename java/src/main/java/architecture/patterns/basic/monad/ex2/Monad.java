package architecture.patterns.basic.monad.ex2;

import lombok.AllArgsConstructor;

import java.util.function.Function;

interface Monad<T, M extends Monad<?, ?>> extends Functor<T, M> {
    M flatMap(Function<T, M> f);
}

@AllArgsConstructor
class CustomMonad<T> implements Monad<T, CustomMonad<?>> {
    private T value;

    @Override
    public <R> CustomMonad<?> map(Function<T, R> f) {
        Function<T, CustomMonad<?>> tmFunction = (value) -> {
            R apply = f.apply(value);
            return (CustomMonad<?>) new CustomMonad<R>(apply);
        };
        return flatMap(tmFunction);
    }

    @Override
    public CustomMonad<?> flatMap(Function<T, CustomMonad<?>> f) {
        return f.apply(value);
    }

    public static <T> CustomMonad<T> of(T a) {
        return new CustomMonad<>(a);
    }
}