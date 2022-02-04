package core.collection.benchmark.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;
import java.util.function.Supplier;

public final class ElementSupplier {

    private static final Random RANDOM = new Random();

    public static <E> Supplier<E> getRandom(final Collection<E> collection) {
        ArrayList<E> list = new ArrayList<>(collection);
        return () -> {
            int index = RANDOM.nextInt(list.size() - 1);
            return list.get(index);
        };
    }

    public static <E> Supplier<E> getRandomAndDiscard(final Collection<E> collection) {
        ArrayList<E> list = new ArrayList<>(collection);
        return () -> {
            int size = list.size();
            int index = RANDOM.nextInt(size - 1);
            E e = list.get(index);
            list.remove(e);
            return e;
        };
    }

    public static <E> Supplier<E> getElementSequentiallyFrom(final Collection<E> collection) {
        ArrayList<E> list = new ArrayList<>(collection);
        final int[] counter = {0};

        return () -> {
            int size = list.size();
            if (size == 0) {
                list.addAll(collection);
            }

            if (counter[0] >= size - 1) {
                counter[0] = 0;
            }

            E e = list.get(counter[0]);
            counter[0]++;
            return e;
        };
    }

    public static <E> Supplier<E> periodicallyFrom(final Collection<E> collection, final int period) {
        int counter = 0;
        ArrayList<E> eachElements = new ArrayList<>();
        for (E e : collection) {
            if (counter % period == 0) {
                eachElements.add(e);
            }
            counter++;
        }

        final int[] elementCounter = {0};
        return () -> {
            if (elementCounter[0] >= eachElements.size()) {
                elementCounter[0] = 0;
            }
            E e = eachElements.get(elementCounter[0]);
            elementCounter[0]++;
            return e;
        };
    }
}
