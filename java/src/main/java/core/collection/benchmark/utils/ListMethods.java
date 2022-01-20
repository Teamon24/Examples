package core.collection.benchmark.utils;

import java.util.Collection;
import java.util.List;
import java.util.function.BiConsumer;

public final class ListMethods {
    public static <T> BiConsumer<Collection<T>, Integer> getByIndex() {
        return (Collection<T> collection, Integer index) -> ((List<T>)collection).get(index);
    }

    public static <T> BiConsumer<Collection<T>, Integer> removeByIndex() {
        return (Collection<T> collection, Integer index) -> ((List<T>)collection).remove((int)index);
    }

    public static <T> TriConsumer<Collection<T>, Integer, T> addByIndex() {
        TriConsumer<Collection<T>, Integer, T> add =
            (Collection<T> collection, Integer index, T element) -> ((List<T>)collection).add(index, element);
        return add;
    }

    public static <T> BiConsumer<Collection<T>, T> addElement() {
        return (Collection<T> collection, T element) -> ((List<T>)collection).add(element);
    }

    public static <T> TriConsumer<Collection<T>, Integer, T> setter() {
        TriConsumer<Collection<T>, Integer, T> set =
            (Collection<T> collection, Integer index, T element) -> ((List<T>)collection).set(index, element);
        return set;
    }
}
