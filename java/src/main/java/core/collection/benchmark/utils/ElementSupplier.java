package core.collection.benchmark.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;
import java.util.function.Supplier;

public final class ElementSupplier {

    private static Random random = new Random();

    public static <E> Supplier<E> getRandomElementAndForgetHimFrom(final Collection<E> collection) {
        ArrayList<E> list = new ArrayList<>(collection);
        return () -> {
            int size = list.size();
            int index = random.nextInt(size - 1);
            E e = list.get(index);
            list.remove(e);
            return e;
        };
    }

    public static <E> Supplier<E> sequenceElementSupplier(final Sequence<E> sequence) {
        return sequence::next;
    }
}
