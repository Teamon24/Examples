package core.concurrency.thread_pool.forkJoin.ex1;

import core.concurrency.ConcurrencyUtils;
import core.utils.ListGenerator;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.function.BiFunction;
import java.util.stream.Stream;

public class Demo {

    public static void main(String[] args) {

        Random random = new Random();
        int integersAmount = 100;

        ArrayList<Integer> integers = ListGenerator.getRandomIntegerList(random, integersAmount);
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

        Stream.of(sum, max, min, mult, sub)
            .forEach(task -> {
                Object result = execute(forkJoinPool, task);
                assertResult(task, result);
                printResult(task, result);
            });
        ConcurrencyUtils.sleep(100);
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
            System.out.println(message);
        }
    }

    private static int getParallelism(int availableProcessors) {
        if (availableProcessors == 1) return 1;
        if (availableProcessors == 2) return 1;
        return availableProcessors / 2;
    }

    private static void printResult(Reduce<?> task, Object result) {
        String message = String.format("Result of '%s': %s", task.getName(), result);
        String line = "-".repeat(message.length());
        System.out.println(line);
        System.out.println(message);
        System.out.println(line.repeat(4));
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