package core.concurrency.package_review.completable_future;

import lombok.val;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.concurrent.Executors.newCachedThreadPool;
import static utils.ConcurrencyUtils.get;
import static utils.ConcurrencyUtils.sleep;
import static utils.ConcurrencyUtils.terminate;
import static utils.ConcurrencyUtils.threadPrintfln;
import static utils.ConcurrencyUtils.threadPrintln;
import static utils.ConcurrencyUtils.threadPrintlnTitle;

public class Demo {
    public static void main(String[] args) {
        asFuture();
        withEncapsulatedLogic();
        processResultOfAsyncComputation();
        combiningFutures();
        runningMultipleFuturesParallel();
        handlingErrors();
        exceptionHandling();
    }

    private static void asFuture() {
        threadPrintlnTitle("asFuture");
        val future = new CompletableFuture<>();

        val executorService = newCachedThreadPool();

        executorService.submit(() -> {
            Thread.sleep(500);
            future.complete("Hello");
            return null;
        });

        threadPrintfln("result: %s", get(future));
        terminate(executorService);
        System.out.println();
    }

    private static void withEncapsulatedLogic() {
        threadPrintlnTitle("withEncapsulatedLogic");
        val future = supplyAsync(() -> "Hello");
        threadPrintfln("result: %s", get(future));
        System.out.println();
    }

    private static void processResultOfAsyncComputation() {
        threadPrintlnTitle("processResultOfAsyncComputation");
        val task =
            supplyAsync(1, () -> "Hello")
                .thenApply(s -> s + " World");

        threadPrintfln("result: %s", get(task));

        get(supplyAsync(2, () -> "Hello")
            .thenAccept(s -> threadPrintfln("thenAccept result: %s", s)));

        get(supplyAsync(3, () -> "Hello")
            .thenRun(() -> threadPrintln("thenRun result: computation finished.")));

        System.out.println();
    }

    private static void combiningFutures() {
        threadPrintlnTitle("combiningFutures");
        val future =
            supplyAsync(1, () -> "Hello")
                .thenCompose(s -> supplyAsync(2, () -> s + " World 1"));

        val future2 =
            supplyAsync(3, () -> "Hello")
                .thenCombine(
                    supplyAsync(4, () -> " World 2"), String::concat);

        val future3 =
            supplyAsync(5, () -> "Hello")
                .thenAcceptBoth(
                    supplyAsync(6, () -> " World 3"),
                    (s1, s2) -> threadPrintfln("result: %s", s1 + s2));

        threadPrintfln("future  result: %s", get(future));
        threadPrintfln("future2 result: %s", get(future2));
        threadPrintfln("future3 result: %s", get(future3));
        System.out.println();
    }

    private static void runningMultipleFuturesParallel() {
        threadPrintlnTitle("runningMultipleFuturesParallel");

        val future2 = supplyAsync(2, sleep(1600, () -> "Beautiful"));
        val future1 = supplyAsync(1, sleep(500, () -> "Hello"));
        val future3 = supplyAsync(3, sleep(800, () -> "World"));

        CompletableFuture.allOf(future1, future2, future3);

        threadPrintfln("result: %s" , get(future1));
        threadPrintfln("result: %s" , get(future2));
        threadPrintfln("result: %s" , get(future3));

        String combined = Stream.of(future1, future2, future3)
            .map(CompletableFuture::join)
            .collect(Collectors.joining(" "));

        threadPrintfln("stream map result - %s", combined);
        System.out.println();
    }

    private static void handlingErrors() {
        threadPrintlnTitle("handlingErrors");

        val future =
            supplyAsync(() -> {
                throw new RuntimeException("Computation error!");
            }).handle((result, throwable) -> {
                sleep(5);
                return result != null ? result : "\"" + throwable.getMessage() + "\"";
            });

        threadPrintfln("handingErrors: %s", get(future));

        val future1 = supplyAsync(() -> null);
        future1.completeExceptionally(new RuntimeException("completed exceptionally"));

        threadPrintfln("result: %s", get(future1));
        System.out.println();
    }

    private static void exceptionHandling() {
        threadPrintlnTitle("Exception handling");
        int initialValue = 0;
        threadPrintfln("initialValue: %s", initialValue);
        String methodName = "thenApplyAsync";

        Function<Integer, Integer> increment = (i) -> {
            threadPrintfln(methodName + " executions: " + i);
            return ++i;
        };

        Function<Integer, Integer> divideByZero = (i) -> {
            threadPrintfln(methodName + " executions: " + i);
            return i / 0;
        };

        Integer handlingResult = get(supplyAsync(() -> initialValue)
            .thenApplyAsync(increment)
            .thenApplyAsync(increment)
            .thenApplyAsync(divideByZero)
            .thenApplyAsync(increment)
            .handleAsync((i, throwable) -> {
                    threadPrintfln("handleAsync execution");
                    threadPrintln("stackTrace:");
                    throwable.printStackTrace();
                    return Integer.MAX_VALUE;
                }
            ));

        threadPrintfln("result after exception handling: %s", handlingResult);
    }

    private static <U> CompletableFuture<U> supplyAsync(Supplier<U> supplier) {
        return supplyAsync(null, supplier);
    }

    private static <U> CompletableFuture<U> supplyAsync(Integer number, Supplier<U> supplier) {
        return CompletableFuture.supplyAsync(() -> {
            if (number != null) {
                threadPrintfln("supplyAsync #%s execution", number);
            } else {
                threadPrintln("supplyAsync execution");
            }
            return supplier.get();
        });
    }
}
