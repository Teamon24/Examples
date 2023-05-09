package utils;

import org.apache.commons.lang3.StringUtils;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public final class ListGenerator {

    public static <T> List<T> create(int size, Supplier<T> creator) {
        ArrayList<T> ts = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            ts.add(creator.get());
        }

        return ts;
    }

    public static List<BigInteger> getBigIntegers(Random random, int integersAmount) {
        return getRandomIntegerList(random, integersAmount, 100).stream().map(BigInteger::valueOf).collect(Collectors.toList());
    }

    public static List<Integer> getRandomIntegerList(final Random random, int size, int bound) {
        System.out.println("Creating integers");
        ArrayList<Integer> integers = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            integers.add(random.nextInt(bound + 1));
        }
        System.out.println("Done with creating");
        System.out.println("Elements: " + StringUtils.joinWith(", ", integers));

        return integers;
    }

    public static <T> List<T> create(Function<List, T> constructor, List<?>... possibleArgs) {
        if (possibleArgs.length == 0)
            throw new IllegalArgumentException("Possible arguments arrays should be at least one");

        List<List> product = NullableCartesianProduct.product(possibleArgs);
        return product.stream().map(constructor).collect(Collectors.toList());
    }
}
