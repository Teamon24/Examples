package core.collection.benchmark.utils;

import utils.RandomUtils;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

public final class IndexSuppliers {

    public static <E> Function<Collection<E>, Integer> supplyLast() {
        return IndexSuppliers::getLast;
    }

    public static <E> Function<Collection<E>, Integer> supplyMiddle() {
        return IndexSuppliers::getMiddle;
    }

    public static <E> Function<Collection<E>, Integer> supplyFirst() {
        return IndexSuppliers::getFirst;
    }


    public static <E> Function<Collection<E>, Integer> supplyRandomIndex() {
        return RandomUtils::randomIndexFrom;
    }

    public static <E> Function<Collection<E>, Integer> supplyThreeIndexes() {
        final AtomicInteger counter = new AtomicInteger(0);
        return (collection) -> {
            int count = counter.get();
            if (count == 1) {
                counter.set(count + 1);
                return getMiddle(collection);
            }
            else if (count == 2) {
                counter.set(count+1);
                return getLast(collection);
            } else {
                counter.set(1);
                return getFirst(collection);
            }
        };
    }

    private static int getFirst(Collection<?> collection) {
        throwIfEmpty(collection);
        return 0;
    }

    private static int getLast(final Collection<?> collection) {
        throwIfEmpty(collection);
        return collection.size() - 1;
    }

    private static int getMiddle(final Collection<?> collection) {
        throwIfEmpty(collection);
        return (collection.size() >> 1) - 1;
    }

    private static void throwIfEmpty(final Collection<?> collection) {
        if (collection.isEmpty()) {
            throw new IllegalArgumentException("During index supplying collection was empty.");
        }
    }
}
