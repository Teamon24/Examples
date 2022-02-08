package utils;

import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

import static utils.PrintUtils.*;
import static utils.PrintUtils.printfln;

public final class ConcurrencyUtils {

    public static final int MILLIS_IN_SECOND = 1000;

    public static void threadPrintln(String template, String message) {
        if (StringUtils.isNotEmpty(message)) {
            ConcurrencyUtils.syncPrintfln(String.format(template, threadName(), message));
        }
    }

    public static void threadPrintln(String message) {
        if (StringUtils.isNotEmpty(message)) {
            ConcurrencyUtils.syncPrintfln("%s: %s", threadName(), message);
        }
    }

    public static void syncPrintln(String s) {
        synchronized (System.out) {
            println(s);
        }
    }

    public static <T> void syncPrintfln(String template, T... args) {
        synchronized (System.out) {
            printfln(template, args);
        }
    }

    public static String threadName() {
        return threadName("[%s]", Thread.currentThread().getName().toUpperCase(Locale.ROOT));
    }

    public static String threadName(String nameTemplate, Object arg) {
        return String.format(nameTemplate, arg);
    }

    public static <T> T call(final Callable<T> callable) {
        try {
            return callable.call();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void sleep(final long millis) {
        ConcurrencyUtils.sleep(millis, "");
    }

    public static void sleep(final long millis, String message) {
        try {
            threadPrintln(message);
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static <T extends Thread> List<T> createThreads(
        final int amount,
        final Function<Integer, T> constructor
    ) {
        List<T> threads = new ArrayList<>();
        for (int i = 1; i <= amount; i++) {
            threads.add(constructor.apply(i));
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

    public static void executeAll(
        final ExecutorService executorService,
        final List<Runnable> tasks
    ) {
        tasks.forEach(executorService::execute);
    }

    public static <T> List<Future<T>> submitAll(
        final ExecutorService executorService,
        final List<Callable<T>> tasks
    ) {
        return tasks.stream().map(executorService::submit).collect(Collectors.toList());
    }

    public static <T> List<Future<T>> invokeAll(
        final ExecutorService executorService,
        final List<? extends Callable<T>> tasks
    ) {
        try {
            return executorService.invokeAll(tasks);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public static <T> T invokeAny(
        final ThreadPoolExecutor executor,
        final List<Callable<T>> tasks
    ) {
        try {
            return executor.invokeAny(tasks);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Nullable
    public static <T> T get(final Future<T> task) {
        try {
            return task.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> List<T> getAll(
        final ExecutorService executorService,
        final List<? extends Callable<T>> tasks
    ) {
        List<Future<T>> futures = invokeAll(executorService, tasks);
        return futures.stream().map(ConcurrencyUtils::get).collect(Collectors.toList());
    }

    public static <T> List<T> getAll(List<? extends Callable<T>> tasks) {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        List<T> results = getAll(executorService, tasks);
        shutdown(executorService, 1000);
        return results;
    }

    public static void shutdown(
        final ExecutorService executorService,
        final int millisTimeout
    ) {
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(millisTimeout, TimeUnit.MILLISECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
        }
    }
}
