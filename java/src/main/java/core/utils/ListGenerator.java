package core.utils;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

public final class ListGenerator {

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
            integers.add(random.nextInt(1, 100));
        }
        System.out.println("Done with creating");
        System.out.println("Elements: " + StringUtils.joinWith(", ", integers));

        return integers;
    }

    public static List<BigInteger> getBigIntegers(Random random, int integersAmount) {
        return getRandomIntegerList(random, integersAmount).stream().map(BigInteger::valueOf).toList();
    }
}
