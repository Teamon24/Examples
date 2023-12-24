package _misc.fizzbuzz;

import com.google.common.collect.Streams;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.IntPredicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

class FizzBuzzTest {
    public static final HashMap<Integer, String> map =
        new HashMap<>() {{
            put(7, "Muzz");
            put(2, "Tizz");
            put(5, "Buzz");
            put(3, "Fizz");
        }};

    private static FizzBuzz fizzBuzz = new FizzBuzz(map);

    public static Stream<Arguments> TestData() {
        List<Arguments> collect = generatePowerset(fizzBuzzDivs())
            .stream()
            .filter(collection -> !collection.isEmpty())
            .sorted(Comparator.comparingInt(List::size))
            .map(set -> {
                List<Map.Entry<Integer, String>> right = map.entrySet().stream()
                    .filter(en -> set.contains(en.getKey())).collect(Collectors.toList());

                String expected = right.stream().map(Map.Entry::getValue).reduce(String::concat).get();

                return Arguments.of(mult(set), expected);
            }).collect(Collectors.toList());


        return Streams.concat(
            collect.stream(),
            fizzbuzzStream()
                .map(entry -> Arguments.of(entry.getKey() * 11, entry.getValue()))
        );
    }

    private static String fizzBuzzNames() {
        fizzBuzz = new FizzBuzz(map);
        return StringUtils.join(fizzBuzz.map.values().toArray());
    }

    private static Integer mult(Collection<Integer> integers) {
        return integers.stream().reduce((i, i2) -> i * i2).get();
    }

    private static List<Integer> fizzBuzzDivs() {
        return new ArrayList<>(fizzBuzz.map.keySet());
    }

    private static Stream<Map.Entry<Integer, String>> fizzbuzzStream() {
        return fizzBuzz.map.entrySet()
            .stream();
    }

    /**
     * Test for {@link FizzBuzz#getName(Integer)}.
     */
    @ParameterizedTest
    @MethodSource("TestData")
    public void getNameTest(Integer number, String expected) {
        String actual = fizzBuzz.getName(number);
        Assertions.assertEquals(expected, actual);
    }

    /**
    * Test for {@link FizzBuzz#filter(Collection)}.
    */
    @ParameterizedTest
    @MethodSource("filterTestData")
    public void filterTest(Collection<Integer> numbers, Collection<Integer> expected) {
        Collection<Integer> actual = fizzBuzz.filter(numbers);
        Assertions.assertEquals(expected, actual);
    }

    public static Stream<Arguments> filterTestData() {
        IntPredicate modPredicate = fizzBuzz.map.keySet().stream()
            .map(FizzBuzzTest::mod)
            .reduce(IntPredicate::or).get();

        return Stream.of(
            Arguments.of(toList(getRange()), toList(getRange().filter(modPredicate)))
        );
    }

    private static IntPredicate mod(Integer i) {
        return n -> n % i == 0;
    }

    private static IntStream getRange() {
        return IntStream.range(1, 35);
    }

    private static List<Integer> toList(IntStream range) {
        return range.boxed().collect(Collectors.toList());
    }

    private static <T> void powersetInternal(
        List<T> set, List<List<T>> powerset, List<T> accumulator, int index)
    {
        if (index == set.size()) {
            powerset.add(new ArrayList<>(accumulator));
        } else {
            accumulator.add(set.get(index));
            powersetInternal(set, powerset, accumulator, index + 1);
            accumulator.remove(accumulator.size() - 1);
            powersetInternal(set, powerset, accumulator, index + 1);
        }
    }

    public static <T> List<List<T>> generatePowerset(List<T> sequence) {
        List<List<T>> powerset = new ArrayList<>();
        powersetInternal(sequence, powerset, new ArrayList<>(), 0);
        return powerset;
    }
}