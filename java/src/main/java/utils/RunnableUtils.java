package utils;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import static utils.ConcurrencyUtils.fixedThreadPool;
import static utils.ConcurrencyUtils.terminate;

public final class RunnableUtils {

    public static List<Future<?>> submitAll(
        final ExecutorService executorService,
        final List<? extends Runnable> tasks
    ) {
        return tasks.stream().map(executorService::submit).collect(Collectors.toList());
    }

    public static void executeAll(
        final ExecutorService executorService,
        final List<Runnable> tasks
    ) {
        tasks.forEach(executorService::execute);
    }

    public static void waitAll(List<Future<?>>... futures) {
        Arrays.stream(futures)
            .flatMap(Collection::stream)
            .forEach(ConcurrencyUtils::get);
    }

    public static void waitAndTerminate(Runnable... runnables) {
        ExecutorService service = fixedThreadPool(runnables.length);
        waitAll(submitAll(service, Arrays.asList(runnables)));
        terminate(service);
    }

    public static void waitAll(
        ExecutorService executorService,
        Runnable... runnables
    ) {
        waitAll(submitAll(executorService, Arrays.asList(runnables)));
    }

    public static void waitAll(
        ExecutorService executorService,
        List<? extends Runnable> runnables
    ) {
        waitAll(submitAll(executorService, runnables));
    }

    public static void waitOne(Future future) {
        ConcurrencyUtils.get(future);
    }

    public static void waitOne(ExecutorService barrierScannerService, Runnable runnable) {
        waitOne(barrierScannerService.submit(runnable));
    }
}
