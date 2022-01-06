package core.concurrency.thread_pool;

import com.google.common.util.concurrent.ListeningExecutorService;
import core.concurrency.ConcurrencyUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

public final class ThreadPoolExamplesUtils {

    public static final int TASK_SLEEP_SECONDS = 2;

    public static Callable<String> task(final int taskNumber, long sleepTime) {
        return () -> {
            String currentThreadName = ConcurrencyUtils.threadName().toUpperCase(Locale.ROOT);
            System.out.printf("%s: executing task#%s\n", currentThreadName, taskNumber);
            Thread.sleep(sleepTime);
            System.out.printf("%s: done with task#%s\n", currentThreadName, taskNumber);
            return String.format("task#%s was done", taskNumber);
        };
    }

    public static List<Callable<String>> createTasks(final int taskAmount, long sleepTime) {
        final List<Callable<String>> callables = new ArrayList<>();
        for (int number = 0; number < taskAmount; number++) {
            callables.add(task(number, sleepTime));
        }
        return callables;
    }

    public static List<Future<String>> submitEach(final ExecutorService executor, final int taskAmount, long sleepTime) {
        final List<Future<String>> futureTasks = new ArrayList<>();
        for (int number = 0; number < taskAmount; number++) {
            futureTasks.add(executor.submit(task(number, sleepTime)));
        }
        return futureTasks;
    }

    public static void print(ThreadPoolExecutor executor, int taskAmount) {
        long start = System.currentTimeMillis();
        int checkTime = getCheckTime(taskAmount);
        while (System.currentTimeMillis() - start < checkTime) {
            String currentThreadName = ConcurrencyUtils.threadName().toUpperCase(Locale.ROOT);
            System.out.printf("---------- %s -----------\n", currentThreadName);
            System.out.printf("pool size: %s\n", executor.getPoolSize());
            System.out.printf("queue size: %s\n", executor.getQueue().size());
            System.out.println("---------------------------");
            ConcurrencyUtils.sleep(ThreadPoolExamplesUtils.TASK_SLEEP_SECONDS * ConcurrencyUtils.MILLIS_IN_SECOND);
        }
    }

    private static int getCheckTime(final int taskAmount) {
        return ((taskAmount + 1) * ThreadPoolExamplesUtils.TASK_SLEEP_SECONDS) * ConcurrencyUtils.MILLIS_IN_SECOND;
    }

    public static Runnable infiniteLoop() {
        return () -> {
            while(true) {
                System.out.println("In the infinite loop");
                ConcurrencyUtils.sleep(1000);
            }
        };
    }

    public static void invokeAll(final ListeningExecutorService executorService,
                                 final List<Callable<String>> tasks)
    {
        try {
            executorService.invokeAll(tasks);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
