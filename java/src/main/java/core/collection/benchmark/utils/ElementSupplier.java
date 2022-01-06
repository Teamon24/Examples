package core.collection.benchmark.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;
import java.util.function.Supplier;

public final class ElementSupplier {

    private static final Random RANDOM = new Random();

    public static <E> Supplier<E> getRandomElementAndForgetHimFrom(final Collection<E> collection) {
        ArrayList<E> list = new ArrayList<>(collection);
        return () -> {
            int size = list.size();
            int index = RANDOM.nextInt(size - 1);
            E e = list.get(index);
            list.remove(e);
            return e;
        };
    }
}
