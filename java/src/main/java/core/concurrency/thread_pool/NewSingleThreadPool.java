package core.concurrency.thread_pool;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class NewSingleThreadPool {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(helloWorldBy("Executor"));

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        Future<?> runnableFuture = executorService.submit(helloWorldBy("ExecutorService"));
        Future<String> callableFuture = executorService.submit(() -> "Hello World");

        runnableFuture.get();
        callableFuture.get();
        executorService.awaitTermination(1, TimeUnit.MICROSECONDS);
        executorService.shutdownNow();
        System.out.println("Shutdowned: " + executorService.isShutdown());
        System.out.println("Terminated: " + executorService.isTerminated());
        System.out.println("The end");

    }

    private static Runnable helloWorldBy(final String name) {
        return () -> System.out.printf("%s: Hello World\n", name);
    }
}
