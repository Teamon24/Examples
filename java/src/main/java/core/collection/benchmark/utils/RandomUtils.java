package core.collection.benchmark.utils;

import java.util.Collection;
import java.util.Iterator;
import java.util.Random;

public final class RandomUtils {

    public static final Random random = new Random();

    public static int random(int fromInclusive, int toExclusive) {
        return fromInclusive + (int) (Math.random() * ((toExclusive - fromInclusive) + 1));
    }

    public static <E> int randomIndexFrom(Collection<E> collection) {
        return random.nextInt(collection.size());
    }

    public static <E> E randomFrom(Collection<E> collection) {
        int index = randomIndexFrom(collection);
        Iterator<E> iterator = collection.iterator();
        for (int i = 0; i <= index && iterator.hasNext(); i++) {
            if (i == index) {
                return iterator.next();
            }
        }
        throw new RuntimeException("Random element was not found");
    }
}
