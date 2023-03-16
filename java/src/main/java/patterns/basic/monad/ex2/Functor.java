package patterns.basic.monad.ex2;

import com.google.common.collect.ImmutableList;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.function.Function;

interface Functor<T, F extends Functor<?, ?>> {
    <R> F map(Function<T, R> f);
}

@AllArgsConstructor
class Identity<T> implements Functor<T, Identity<?>> {

    private final T value;

    @Override
    public <R> Identity<R> map(Function<T,R> f) {
        final R result = f.apply(value);
        return new Identity<>(result);
    }
}


@AllArgsConstructor
class ListFunctor<T> implements Functor<T, ListFunctor<?>> {

    private final ImmutableList<T> list;

    ListFunctor(Iterable<T> value) {
        this.list = ImmutableList.copyOf(value);
    }

    @Override
    public <R> ListFunctor<R> map(Function<T, R> f) {
        ArrayList<R> result = new ArrayList<>(list.size());
        for (T t : list) {
            result.add(f.apply(t));
        }
        return new ListFunctor<>(result);
    }
}

class Nullable<T> implements Functor<T, Nullable<?>> {

    private final T valueOrNull;

    private Nullable(T valueOrNull) {
        this.valueOrNull = valueOrNull;
    }

    @Override
    public <R> Nullable<R> map(Function<T, R> f) {
        if (valueOrNull == null)
            return empty();
        else
            return of(f.apply(valueOrNull));
    }

    public T join() {
        return this.valueOrNull;
    }

    public static <T> Nullable<T> of(T a) {
        return new Nullable<>(a);
    }

    public static <T> Nullable<T> empty() {
        return new Nullable<>(null);
    }
}