package core.concurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.function.Function;

public class ConcurrencyUtils {
    public static final int MILLIS_IN_SECOND = 1000;

    public static String threadName() {
        return Thread.currentThread().getName();
    }

    public static void sleep(final int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void count(final int millis) {
        int seconds = millis / MILLIS_IN_SECOND;
        int restMillis = millis % MILLIS_IN_SECOND;
        for (int i = 0; i < seconds; i++) {
            System.out.println("count: " + i);
            sleep(MILLIS_IN_SECOND);
        }
        sleep(restMillis);
    }

    public static <T> List<T> createThreads(final int amount,
                                            final Function<Integer, T> create) {
        List<T> threads = new ArrayList<>();
        for (int i = 1; i <= amount; i++) {
            threads.add(create.apply(i));
        }
        return threads;
    }

    public static void join(final List<Thread> threads) {
        try {
            for (Thread thread : threads) {
                thread.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static <T> List<Future<T>> invokeAll(final ThreadPoolExecutor executor,
                                                final List<Callable<T>> tasks)
    {
        try {
            return executor.invokeAll(tasks);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T get(final Future<T> task) {
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
