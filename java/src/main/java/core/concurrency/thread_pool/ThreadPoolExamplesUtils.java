package core.concurrency.thread_pool;

import core.collection.benchmark.utils.Sequence;
import utils.PrintUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.function.Function;

import static utils.ConcurrencyUtils.MILLIS_IN_SECOND;
import static utils.ConcurrencyUtils.sleep;
import static utils.ConcurrencyUtils.threadName;
import static utils.ConcurrencyUtils.threadPrintln;

public final class ThreadPoolExamplesUtils {

    public static final int TASK_SLEEP_SECONDS = 2;
    public static final long SLEEP_TIME = (long) TASK_SLEEP_SECONDS * MILLIS_IN_SECOND;

    public static Callable<String> task(final int taskNumber) {
        return () -> {
            printTask(taskNumber, SLEEP_TIME);
            return String.format("task#%s was done", taskNumber);
        };
    }

    public static void printTask(int taskNumber, long taskWorkImitationTime) {
        printTask(taskNumber, "", taskWorkImitationTime);
    }

    public static void printTask(int taskNumber, String title, long taskWorkImitationTime) {
        String titleResult = title.isEmpty() ? "" : "[" + title + "]";
        PrintUtils.printfln("%s%s: executing task#%s", threadName(), titleResult, taskNumber);
        sleep(taskWorkImitationTime);
        PrintUtils.printfln("%s%s: task#%s DONE", threadName(), titleResult, taskNumber);
    }

    public static List<Callable<String>> getTasks(final Sequence<Integer> sequence) {
        return getTasks("", sequence);
    }

    public static List<Callable<String>> getTasks(final String methodName,
                                                  final Sequence<Integer> sequence)
    {
        final List<Callable<String>> callables = new ArrayList<>();
        fill(callables, task(methodName), sequence);
        return callables;
    }

    public static List<Runnable> getRunnables(final String methodName,
                                              final Sequence<Integer> sequence)
    {
        final List<Runnable> runnables = new ArrayList<>();
        fill(runnables, runnable(methodName), sequence);
        return runnables;
    }

    public static List<Future<String>> submitAll(final ExecutorService executor,
                                                 final int taskAmount,
                                                 final Function<Integer, Callable<String>> getCallable)
    {
        final List<Future<String>> futureTasks = new ArrayList<>();
        for (int number = 0; number < taskAmount; number++) {
            Future<String> future = executor.submit(getCallable.apply(number));
            futureTasks.add(future);
        }
        return futureTasks;
    }

    public static void printPoolAndQueueSizes(ThreadPoolExecutor executor, int taskAmount) {
        long start = System.currentTimeMillis();
        int checkTime = getCheckTime(taskAmount);
        while (System.currentTimeMillis() - start < checkTime) {
            String message = String.format("pool size: %s; queue size: %s", executor.getPoolSize(), executor.getQueue().size());
            threadPrintln(message);
            sleep(SLEEP_TIME);
        }
    }

    public static Runnable infiniteLoop() {
        return () -> {
            while (true) {
                System.out.println("In the infinite loop");
                sleep(1);
            }
        };
    }

    private static Function<Integer, Callable<String>> task(String methodName) {
        return (number) -> () -> {
            printTask(number, methodName, SLEEP_TIME);
            return "Task result";
        };
    }

    private static Function<Integer, Runnable> runnable(String methodName) {
        return (number) -> () -> call(task(methodName).apply(number));
    }

    public static <T> T call(final Callable<T> callable) {
        try {
            return callable.call();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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

    private static int getCheckTime(final int taskAmount) {
        return (taskAmount * TASK_SLEEP_SECONDS) * MILLIS_IN_SECOND;
    }
}
