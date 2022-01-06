package core.collection.benchmark.utils;

import java.util.Collection;
import java.util.function.Function;

public final class IndexSuppliers {

    public static <E> Function<Collection<E>, Integer> lastIndex() {
        return IndexSuppliers::getLast;
    }

    public static int getLast(final Collection<?> collection) {
        return collection.size() - 1;
    }

    public static <E> Function<Collection<E>, Integer> middleIndex() {
        return IndexSuppliers::getMiddle;
    }

    public static int getMiddle(final Collection<?> collection) {
        return (collection.size() >> 1) - 1;
    }

    public static <E> Function<Collection<E>, Integer> firstIndex() {
        return (Collection<E> collection) -> getFirst();
    }

    public static int getFirst() {
        return 0;
    }
}
