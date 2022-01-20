package core.collection.benchmark.utils;

import java.util.Collection;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

public final class IndexSuppliers {

    public static <E> Function<Collection<E>, Integer> lastIndex() {
        return IndexSuppliers::getLast;
    }

    public static <E> Function<Collection<E>, Integer> middleIndex() {
        return IndexSuppliers::getMiddle;
    }

    public static <E> Function<Collection<E>, Integer> firstIndex() {
        return IndexSuppliers::getFirst;
    }

    public static int getFirst(Collection<?> collection) {
        return 0;
    }

    public static int getLast(final Collection<?> collection) {
        return collection.size() - 1;
    }

    public static int getMiddle(final Collection<?> collection) {
        return (collection.size() >> 1) - 1;
    }

    public static <E> Function<Collection<E>, Integer> getRandomIndex() {
        final Random random = new Random();
        return (Collection<E> collection) -> random.nextInt(collection.size());
    }

    public static <E> Function<Collection<E>, Integer> getThreeIndexes() {
        final AtomicInteger counter = new AtomicInteger(0);
        return (collection) -> {
            int count = counter.get();
            if (count == 1) {
                counter.set(count+1);
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
}
