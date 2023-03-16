package utils;

import core.concurrency.package_review.thread_factory.CustomThreadFactory;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ConcurrencyUtils {

    public static final String THREAD_MESSAGE_TEMPLATE = "%s %s";
    public static final String THREAD_NAME_TEMPLATE = "[%s]";

    public static final int MILLIS_IN_SECOND = 1000;

    public static void threadPrintfln(String template, Object... args) {
        String message = String.format(template, args);
        PrintUtils.printfln(THREAD_MESSAGE_TEMPLATE, threadName(), message);
    }

    public static void threadPrintlnTitle(String template) {
        syncPrintln(() -> {
            threadPrintln("-".repeat(100));
            ConcurrencyUtils.threadPrintln(template);
            threadPrintln("-".repeat(100));
        });
    }

    public static void threadPrintflnTitle(String template, Object... args) {
        syncPrintln(() -> {
            threadPrintln("-".repeat(100));
            ConcurrencyUtils.threadPrintfln(template, args);
            threadPrintln("-".repeat(100));
        });
    }

    public static void threadPrintln(String message) {
        if (StringUtils.isNotEmpty(message)) {
            PrintUtils.printfln(THREAD_MESSAGE_TEMPLATE, threadName(), message);
        }
    }

    public static void threadPrintln(Object object) {
        PrintUtils.printfln(THREAD_MESSAGE_TEMPLATE, threadName(), object);
    }

    public static void syncPrintln(Invoker block) {
        synchronized (System.out) {
            block.invoke();
        }
    }

    public static String threadName() {
        return threadName(THREAD_NAME_TEMPLATE, Thread.currentThread().getName());
    }

    public static String threadName(String nameTemplate, Object arg) {
        return String.format(nameTemplate, arg);
    }

    public static void sleep(final int seconds) {
        ConcurrencyUtils.sleep(seconds * 1000L);
    }

    public static void sleep(final long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void setThreadName(String name) {
        Thread.currentThread().setName(name);
    }


    public static String getThreadName() {
        return Thread.currentThread().getName();
    }

    public static void interrupt() {
        Thread.currentThread().interrupt();
        ConcurrencyUtils.threadPrintln("interrupted");
    }

    public static <T extends Thread> List<T> createThreads(
        final int size,
        final Function<Integer, T> constructor
    ) {
        List<T> threads = new ArrayList<>();
        for (int i = 1; i <= size; i++) {
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

    public static void join(final Thread... threads) {
        try {
            for (Thread thread : threads) {
                thread.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Nullable
    public static <T> T get(final Future<T> task) {
        try {
            return task.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void terminate(final ExecutorService... executorServices) {
        if (executorServices.length == 0) throw new RuntimeException("There are no services to terminate");
        terminate(Long.MAX_VALUE, executorServices);
    }

    public static void terminate(
        final long millisTimeout,
        final ExecutorService... executorServices
    ) {
        if (executorServices.length == 0) throw new RuntimeException("There are no services to terminate");
        Arrays.stream(executorServices).forEach(it -> ConcurrencyUtils.terminate(millisTimeout, it));
    }

    public static void terminate(
        final long millisTimeout,
        final ExecutorService executorService
    ) {
        executorService.shutdown();
        try {
            boolean awaitTermination = executorService.awaitTermination(
                millisTimeout, TimeUnit.MILLISECONDS);

            if (!awaitTermination) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
        }
    }

    public static void await(CountDownLatch countDownLatch) {
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            ConcurrencyUtils.interrupt();
            e.printStackTrace();
        }
    }

    public static void await(CyclicBarrier cyclicBarrier) {
        try {
            cyclicBarrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            ConcurrencyUtils.interrupt();
            e.printStackTrace();
        }
    }

    public static <T> List<T> getAll(List<? extends Future<T>> futures) {
        return futures.stream().map(ConcurrencyUtils::get).collect(Collectors.toList());
    }

    public static ExecutorService fixedThreadPool(int nThreads, String poolName) {
        return Executors.newFixedThreadPool(nThreads, new CustomThreadFactory(poolName));
    }

    public static ExecutorService fixedThreadPool(int nThreads) {
        return Executors.newFixedThreadPool(nThreads, noPoolNameFactory());
    }

    public static ExecutorService singleThreadPool(String poolName) {
        return Executors.newSingleThreadExecutor(new CustomThreadFactory(poolName));
    }

    public static CustomThreadFactory noPoolNameFactory() {
        return new CustomThreadFactory("");
    }

}
