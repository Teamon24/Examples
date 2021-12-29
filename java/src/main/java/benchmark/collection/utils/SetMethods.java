package benchmark.collection.utils;

import java.util.Collection;
import java.util.Set;
import java.util.function.BiConsumer;

public final class SetMethods {

    public static <T> BiConsumer<Collection<T>, T> remove() {
        return (Collection<T> collection, T element) -> ((Set<T>)collection).remove(element);
    }

    public static <T> BiConsumer<Collection<T>, T> add() {
        return (Collection<T> collection, T element) -> ((Set<T>)collection).add(element);
    }

    public static <T> TriConsumer<Collection<T>, Integer, T> remover() {
        TriConsumer<Collection<T>, Integer, T> set =
            (Collection<T> collection, Integer index, T element) -> ((Set<T>)collection).remove(element);
        return set;
    }

    public static <T> TriConsumer<Collection<T>, Integer, T> adder() {
        TriConsumer<Collection<T>, Integer, T> get =
            (Collection<T> collection, Integer index, T element) -> ((Set<T>)collection).add(element);
        return get;
    }
}
