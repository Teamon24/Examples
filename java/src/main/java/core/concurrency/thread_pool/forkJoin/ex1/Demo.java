package core.concurrency.thread_pool.forkJoin.ex1;

import org.apache.commons.lang3.tuple.Pair;
import utils.ConcurrencyUtils;
import utils.ListGenerator;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import static java.lang.System.out;

public class Demo {

    public static void main(String[] args) {

        Random random = new Random();
        int integersAmount = 100;

        List<Integer> integers = ListGenerator.getRandomIntegerList(random, integersAmount, 100000);
        List<BigInteger> bigIntegers = ListGenerator.getBigIntegers(random, integersAmount);

        int availableProcessors = Runtime.getRuntime().availableProcessors();
        int parallelism = getParallelism(availableProcessors);

        int threshold = 13;
        int forksAmount = 2;

        Reduce<BigInteger> mult = new Reduce<>("Multiplication", bigIntegers, BigInteger::multiply, threshold, forksAmount);
        Reduce<BigInteger> sub = new Reduce<>("Subtraction", bigIntegers, BigInteger::subtract, threshold, forksAmount);
        Reduce<Integer> min = new Reduce<>("Minimal", integers, Integer::min, threshold, forksAmount);
        Reduce<Integer> max = new Reduce<>("Maximal", integers, Integer::max, threshold, forksAmount);
        Reduce<Integer> sum = new Reduce<>("Sum", integers, Integer::sum, threshold, forksAmount);

        final ForkJoinPool forkJoinPool = new ForkJoinPool(parallelism);
        List<Reduce> tasks = tasks(mult, sub, min, max, sum);
        List<Reduce> exclusions = new ArrayList<>() {{
            add(sub);
            add(min);
            add(max);
            add(sum);
        }};
        Map<Reduce<? extends Number>, Object> results =
            tasks
                .stream()
                .filter(fork -> !exclusions.contains(fork))
                .map(task -> {
                    Object result = execute(forkJoinPool, task);
                    return Pair.of(task, result);
                }).collect(Collectors.toMap(Pair::getKey, Pair::getValue));

        results.forEach((task, result) -> {
            assertResult(task, result);
            printResult(task, result);
        });

        ConcurrencyUtils.sleep(100L);
    }

    private static List<Reduce> tasks(
        Reduce<BigInteger> mult,
        Reduce<BigInteger> sub,
        Reduce<Integer> min,
        Reduce<Integer> max,
        Reduce<Integer> sum
    ) {
        List<Reduce> tasks = new ArrayList<>();
        tasks.add(mult);
        tasks.add(sub);
        tasks.add(min);
        tasks.add(max);
        tasks.add(sum);
        return tasks;
    }

    private static <T> void assertResult(Reduce<T> task, Object actual) {
        BiFunction<T, T, T> reducer = task.getReducer();
        List<T> elements = task.getElements();
        T expected = elements.stream().reduce(reducer::apply).get();
        if (!expected.equals(actual)) {
            String template = "For task '%s' expected result is '%s', but actual - '%s'";
            String message = String.format(template, task.getName(), expected, actual);
            throw new RuntimeException(message);
        } else {
            String template = "For task '%s' expected and actual results are equal - '%s'";
            String message = String.format(template, task.getName(), expected);
            out.println(message);
        }
    }

    private static int getParallelism(int availableProcessors) {
        if (availableProcessors == 1) return 1;
        if (availableProcessors == 2) return 1;
        if (availableProcessors == 3) return 2;
        return availableProcessors / 2;
    }

    private static void printResult(Reduce<?> task, Object result) {
        String message = String.format("Result of '%s': %s", task.getName(), result);
        String line = "-".repeat(message.length());
        out.println(line);
        out.println(message);
        out.println(line.repeat(4));
    }

    private static <T> T execute(ForkJoinPool forkJoinPool, ForkJoinTask<T> task) {
        try {
            forkJoinPool.submit(task);
            return task.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}