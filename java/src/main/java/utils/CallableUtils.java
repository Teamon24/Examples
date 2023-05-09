package utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

public final class CallableUtils {

    public static <T> List<Future<T>> submitAll(
        final ExecutorService executorService,
        final Callable<T>... tasks
    ) {
        return Arrays.stream(tasks).map(executorService::submit).collect(Collectors.toList());
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

    public static <T> List<T> getAll(Collection<Future<T>> futures) {
        return futures.stream().map(ConcurrencyUtils::get).collect(Collectors.toList());
    }

    public static <T> List<T> getAll(List<? extends Callable<T>> tasks, int nThreads) {
        ExecutorService executorService = Executors.newFixedThreadPool(nThreads);
        List<T> results = getAll(executorService, tasks);
        ConcurrencyUtils.terminate(1000, executorService);
        return results;
    }

    public static <T> T invokeAny(
        final ExecutorService executor,
        final List<Callable<T>> tasks
    ) {
        try {
            return executor.invokeAny(tasks);
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
        return getAll(futures);
    }

    public static <T> List<T> getAll(
        final ExecutorService executorService,
        final Callable<T>... tasks
    ) {
        List<Callable<T>> tasksList = Arrays.stream(tasks).collect(Collectors.toList());
        return getAll(executorService, tasksList);
    }
}
