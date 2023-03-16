package core.concurrency.package_review.completable_future;

import lombok.val;
import utils.RandomUtils;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.concurrent.CompletableFuture.allOf;
import static java.util.concurrent.Executors.newCachedThreadPool;
import static utils.ConcurrencyUtils.get;
import static utils.ConcurrencyUtils.terminate;
import static utils.ConcurrencyUtils.threadPrintfln;
import static utils.ConcurrencyUtils.threadPrintln;

public class Demo {
    public static void main(String[] args) {
        asFuture();
        withEncapsulatedLogic();
        processResultOfAsyncComputation();
        combiningFutures();
        runningMultipleFuturesParallel();
        handingErrors();
        example();
    }

    private static void asFuture() {
        val future = new CompletableFuture<>();

        val executorService = newCachedThreadPool();
        executorService.submit(() -> {
            Thread.sleep(500);
            future.complete("Hello");
            return null;
        });

        threadPrintfln("\"asFuture\": %s", get(future));
        terminate(executorService);
    }

    private static void withEncapsulatedLogic() {
        val future = supplyAsync(() -> "Hello");
        threadPrintfln("\"withEncapsulatedLogic\": %s", get(future));
    }

    private static void processResultOfAsyncComputation() {
        val task =
            supplyAsync(() -> "Hello")
                .thenApply(s -> s + " World");

        threadPrintfln("\"processResultOfAsyncComputation\": %s", get(task));

        get(supplyAsync(() -> "Hello")
            .thenAccept(s -> threadPrintfln("\"processResultOfAsyncComputation\": " + s)));

        get(supplyAsync(() -> "Hello")
            .thenRun(() -> threadPrintln("\"processResultOfAsyncComputation\": computation finished.")));
    }

    private static void combiningFutures() {
        val future =
            supplyAsync(() -> "Hello")
                .thenCompose(s -> supplyAsync(() -> s + " World 1"));

        val async1 = supplyAsync(() -> "Hello");
        val async2 = supplyAsync(() -> " World 2");

        val future2 = async1.thenCombine(async2, (s1, s2) -> s1 + s2);

        val future3 =
            supplyAsync(() -> "Hello")
                .thenAcceptBoth(
                    supplyAsync(() -> " World 3"),
                    (s1, s2) -> threadPrintfln("\"combiningFutures\": %s", s1 + s2));

        threadPrintfln("\"combiningFutures\": %s", get(future));
        threadPrintfln("\"combiningFutures\": %s", get(future2));
        threadPrintfln("\"combiningFutures\": %s", get(future3));
    }

    private static void runningMultipleFuturesParallel() {
        val future1 = supplyAsync(() -> "Hello");
        val future2 = supplyAsync(() -> "Beautiful");
        val future3 = supplyAsync(() -> "World");

        val combinedFuture = allOf(future1, future2, future3);

        get(combinedFuture);

        threadPrintfln("\"runningMultipleFuturesParallel\": done - %s, result - %s" , future1.isDone(), get(future1));
        threadPrintfln("\"runningMultipleFuturesParallel\": done - %s, result - %s" , future2.isDone(), get(future2));
        threadPrintfln("\"runningMultipleFuturesParallel\": done - %s, result - %s" , future3.isDone(), get(future3));

        String combined = Stream.of(future1, future2, future3)
            .map(CompletableFuture::join)
            .collect(Collectors.joining(" "));

        threadPrintfln("\"runningMultipleFuturesParallel\": stream map result - %s", combined);
    }

    private static void handingErrors() {
        String name = null;

        val future =
            supplyAsync(() -> {
                if (name == null) {
                    throw new RuntimeException("Computation error!");
                }
                return "Hello, " + name;
            }).handle((result, throwable) -> result != null ? result : "Error has happened");

        threadPrintfln("\"handingErrors\": %s", get(future));

        val future1 = supplyAsync(() -> "Exception");
        future1.completeExceptionally(new RuntimeException("completed exceptionally"));

        threadPrintfln("\"handingErrors\": %s", get(future1));
    }

    private static <U> CompletableFuture<U> supplyAsync(Supplier<U> supplier) {
        return CompletableFuture.supplyAsync(() -> {
            threadPrintfln("supplyAsync execution");
            return supplier.get();
        });
    }

    private static void example() {
        get(supplyAsync(randomInt())
            .thenApplyAsync((i) -> {
                threadPrintfln("thenApplyAsync execution: " + i);
                return randomInt().get() + i;
            })
            .thenApplyAsync((i) -> {
                threadPrintfln("thenApplyAsync execution: " + i);
                return randomInt().get() + i;
            })
            .thenApplyAsync((i) -> {
                threadPrintfln("thenApplyAsync execution: " + i);
                return i / 0;
            })
            .handleAsync((i, throwable) -> {
                    threadPrintfln("handleAsync execution");
                    threadPrintln("stackTrace:");
                    throwable.printStackTrace();
                    return 6;
                }
            ));
    }

    private static Supplier<Integer> randomInt() {
        return () -> RandomUtils.random(1, 10);
    }
}
