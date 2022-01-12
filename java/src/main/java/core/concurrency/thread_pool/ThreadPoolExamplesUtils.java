package core.concurrency.thread_pool;

import core.collection.benchmark.utils.Sequence;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.function.Function;

import static core.concurrency.ConcurrencyUtils.MILLIS_IN_SECOND;
import static core.concurrency.ConcurrencyUtils.call;
import static core.concurrency.ConcurrencyUtils.sleep;
import static core.concurrency.ConcurrencyUtils.threadName;

public final class ThreadPoolExamplesUtils {

    public static final int TASK_SLEEP_SECONDS = 4;
    public static final long SLEEP_TIME = (long) TASK_SLEEP_SECONDS * MILLIS_IN_SECOND;

    public static Callable<String> callable(final int taskNumber) {
        return () -> {
            printTask(taskNumber);
            return String.format("task#%s was done", taskNumber);
        };
    }

    public static void printTask(int taskNumber) {
        String currentThreadName = threadName().toUpperCase(Locale.ROOT);
        System.out.printf("%s: executing task#%s\n", currentThreadName, taskNumber);
        sleep(SLEEP_TIME);
        System.out.printf("%s: DONE with task#%s\n", currentThreadName, taskNumber);
    }

    public static void printTask(int taskNumber, String title) {
        String currentThreadName = threadName().toUpperCase(Locale.ROOT);
        System.out.printf("%s[%s]: executing task#%s\n", currentThreadName, title, taskNumber);
        sleep(SLEEP_TIME);
        System.out.printf("%s[%s]: DONE with task#%s\n", currentThreadName, title, taskNumber);
    }

    public static List<Callable<String>> getCallables(final String methodName,
                                                      final Sequence<Integer> sequence)
    {
        final List<Callable<String>> callables = new ArrayList<>();
        fill(callables, callable(methodName), sequence);
        return callables;
    }

    private static Function<Integer, Callable<String>> callable(String methodName) {
        return (number) -> () -> {
            printTask(number, methodName);
            return "";
        };
    }

    public static List<Runnable> getRunnables(final String methodName,
                                              final Sequence<Integer> sequence)
    {
        final List<Runnable> runnables = new ArrayList<>();
        fill(runnables, runnable(methodName), sequence);

        return runnables;
    }

    private static Function<Integer, Runnable> runnable(String methodName) {
        return (number) -> () -> call(callable(methodName).apply(number));
    }

    private static <T> void fill(List<T> runnables,
                                 Function<Integer, T> getRunnable,
                                 Sequence<Integer> sequence)
    {
        int counter = 0;
        Integer first = sequence.next();
        Integer last = sequence.next();
        for (int number = first; number < last && counter < (last - first); number++) {
            runnables.add(getRunnable.apply(number));
            counter++;
        }
    }

    public static List<Future<String>> submitEach(final ExecutorService executor,
                                                  final int taskAmount,
                                                  final Function<Integer, Callable<String>> getCallable)
    {
        final List<Future<String>> futureTasks = new ArrayList<>();
        for (int number = 0; number < taskAmount; number++) {
            futureTasks.add(executor.submit(getCallable.apply(number)));
        }
        return futureTasks;
    }

    public static void print(ThreadPoolExecutor executor, int taskAmount) {
        long start = System.currentTimeMillis();
        int checkTime = getCheckTime(taskAmount);
        while (System.currentTimeMillis() - start < checkTime) {
            String currentThreadName = threadName().toUpperCase(Locale.ROOT);
            System.out.printf("---------- %s -----------\n", currentThreadName);
            System.out.printf("pool size: %s\n", executor.getPoolSize());
            System.out.printf("queue size: %s\n", executor.getQueue().size());
            System.out.println("---------------------------");
            sleep(SLEEP_TIME);
        }
    }

    private static int getCheckTime(final int taskAmount) {
        return ((taskAmount + 1) * TASK_SLEEP_SECONDS) * MILLIS_IN_SECOND;
    }

    public static Runnable infiniteLoop() {
        return () -> {
            while (true) {
                System.out.println("In the infinite loop");
                sleep(1000);
            }
        };
    }
}
