package core.collection.benchmark.utils;

import java.util.Collection;
import java.util.function.Function;

public final class IndexSuppliers {

    public static <E> Function<Collection<E>, Integer> lastIndex() {
        return (Collection<E> collection) -> collection.size() - 1;
    }

    public static <E> Function<Collection<E>, Integer> middleIndex() {
        return (Collection<E> collection) -> collection.size() / 2;
    }

    public static <E> Function<Collection<E>, Integer> firstIndex() {
        return (Collection<E> collection) -> 1;
    }
}
