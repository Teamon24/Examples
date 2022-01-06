package core.utils;

import java.util.ArrayList;
import java.util.Random;
import java.util.function.Supplier;

public final class ElementUtils {
    public static <T> ArrayList<T> getList(int size, Supplier<T> creator) {
        ArrayList<T> ts = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            ts.add(creator.get());
        }

        return ts;
    }

    public static ArrayList<Integer> getRandomIntegerList(final Random random, int size) {
        System.out.println("Creating integers");
        ArrayList<Integer> integers = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            integers.add(random.nextInt(100));
        }
        System.out.println("Done with creating");

        return integers;
    }

    public static Integer[] getRandomIntegerArray(int size) {
        Random random = new Random();
        Integer[] integers = new Integer[size];
        for (int i = 0; i < size; i++) {
            integers[i] = random.nextInt(100);
        }

        return integers;
    }

    public static int[] getRandomIntArray(int size) {
        Random random = new Random();
        int[] integers = new int[size];
        for (int i = 0; i < size; i++) {
            integers[i] = random.nextInt(100);
        }

        return integers;
    }
}
