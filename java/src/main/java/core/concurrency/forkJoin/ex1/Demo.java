package core.concurrency.forkJoin.ex1;

import core.utils.ElementUtils;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.stream.Stream;

public class Demo {
    public static void main(String[] args) {

        Random random = new Random();
        int integersAmount = 1_000_000;

        ArrayList<Integer> integers = ElementUtils.getRandomIntegerList(random, integersAmount);
        int parallelism = 1;

        Reduce<Integer> sum = new Reduce<>(integers, Integer::sum);
        Reduce<Integer> max = new Reduce<>(integers, Integer::max);
        Reduce<Integer> min = new Reduce<>(integers, Integer::min);
        Reduce<Integer> mult = new Reduce<>(integers, (i1, i2) -> i1 * i2);

        final ForkJoinPool forkJoinPool = new ForkJoinPool(parallelism);
        Stream.of(sum, max, min, mult)
            .forEach(task -> {
                forkJoinPool.submit(task);
                Integer result = execute(task);
                System.out.println(result);
            });
    }

    private static Integer execute(final ForkJoinTask<Integer> task) {
        try {
            return task.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }
}