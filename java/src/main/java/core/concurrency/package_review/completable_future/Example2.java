package core.concurrency.package_review.completable_future;

import utils.ConcurrencyUtils;
import utils.RandomUtils;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static utils.ConcurrencyUtils.threadPrintfln;
import static utils.ConcurrencyUtils.threadPrintln;

public class Example2 {
    public static void main(String[] args) throws Exception {

        ExecutorService exec = Executors.newSingleThreadExecutor();
        CompletableFuture<Integer> f = returnOne(3000L, exec);
        CompletableFuture<Integer> f2;

        if (RandomUtils.random(0, 2) == 1) {
            f2 = f.thenApply((i) -> i + 1);
            threadPrintfln("then +1");
        } else {
            f2 = f.thenApply((i) -> i + 2);
            threadPrintfln("then +2");
        }

        CompletableFuture<Integer> f3;
        if (RandomUtils.random(0, 2) == 1) {
            f3 = f2.thenApply((i) -> i * 3);
            threadPrintfln("then *3");
        } else {
            f3 = f2.thenApply((i) -> i * 4);
            threadPrintfln("then *4");
        }
        
        CompletableFuture<Integer> f4 = f3.thenCombineAsync(returnOne(2000L, exec), Integer::sum);
        threadPrintfln("then sum");
        threadPrintln(f4.get()); // Waits until the "calculation" is done, then
        ConcurrencyUtils.terminate(exec);
    }

    private static CompletableFuture<Integer> returnOne(long millis, ExecutorService exec) {
        return CompletableFuture.supplyAsync(ConcurrencyUtils.sleep(millis, () -> 1), exec);
    }
}
