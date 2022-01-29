package core.collection.benchmark.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
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

    public static <E> Supplier<E> getElementAndDiscard(final Collection<E> collection) {
        ArrayList<E> list = new ArrayList<>(collection);
        final AtomicInteger counter = new AtomicInteger(0);

        return () -> {
            int size = list.size();
            if (size == 0) {
                list.addAll(collection);
            }

            int counterValue = counter.get();

            if (counterValue >= size - 1) {
                counterValue = 0;
                counter.set(counterValue);
            }

            E e = list.get(counterValue);
            list.remove(e);
            counter.set(counterValue + 1);
            return e;
        };
    }

    public static <E> Supplier<E> periodicallyFrom(
        final Collection<E> collection,
        final int period
    ) {
        int counter = 0;
        ArrayList<E> eachElements = new ArrayList<>();
        for (E e : collection) {
            if (counter % period == 0) {
                eachElements.add(e);
            }
            counter++;
        }

        final int[] atomicCounter = {0};
        return () -> {
            if (atomicCounter[0] >= eachElements.size()) {
                atomicCounter[0] = 0;
            }
            E e = eachElements.get(atomicCounter[0]);
            atomicCounter[0]++;
            return e;
        };
    }

    private static int getPeriod(int period, int newPeriod, int size, int i) {
        if (period >= size / i) return size / (i * 2);
        return newPeriod;
    }

}
