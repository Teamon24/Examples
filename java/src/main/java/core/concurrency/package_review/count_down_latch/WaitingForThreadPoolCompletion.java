package core.concurrency.package_review.count_down_latch;

import core.concurrency.package_review.Task;
import utils.ConcurrencyUtils;
import utils.ListGenerator;
import utils.RunnableUtils;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.function.Supplier;

public class WaitingForThreadPoolCompletion {

    public static void main(String[] args) {
        int threadsSize = 3;
        int tasksSize = 10;
        int count = 5;

        CountDownLatch countDownLatch = new CountDownLatch(count);
        ExecutorService executorService = ConcurrencyUtils.fixedThreadPool(threadsSize);

        List<Runnable> tasks = ListGenerator.create(tasksSize, doCount(countDownLatch));

        ExecutorService singleThreadExecutor = ConcurrencyUtils.singleThreadPool("scanner");
        singleThreadExecutor.submit(() -> scanCountDown(count, countDownLatch));
        List<Future<?>> futures = RunnableUtils.submitAll(executorService, tasks);

        ConcurrencyUtils.threadPrintfln("IS AWAITING FOR COUNT DOWN");
        ConcurrencyUtils.await(countDownLatch);
        ConcurrencyUtils.threadPrintfln("COUNT DOWN IS DONE");
        ConcurrencyUtils.threadPrintfln("IS AWAITING FOR JOBS ARE DONE");
        RunnableUtils.waitAll(futures);
        ConcurrencyUtils.threadPrintlnTitle("JOBS ARE DONE");
        ConcurrencyUtils.terminate(executorService, singleThreadExecutor);
    }

    private static Supplier<Runnable> doCount(CountDownLatch countDownLatch) {
        return () -> new Task<>(countDownLatch, WaitingForThreadPoolCompletion::countDown);
    }

    public static void scanCountDown(int limit, CountDownLatch countDownLatch) {
        long count;
        while (true) {
            count = countDownLatch.getCount();
            String message = String.format("is scanning for count down: %s/%s", limit - count, limit);
            ConcurrencyUtils.threadPrintln(message);
            if (count == 0) {
                ConcurrencyUtils.threadPrintln("scanning is done");
                break;
            }
            ConcurrencyUtils.sleep(500L);
        }
    }

    public static void countDown(CountDownLatch it) {
        ConcurrencyUtils.sleep(1);
        if (it.getCount() != 0) {
            it.countDown();
            ConcurrencyUtils.threadPrintln("job is counting down: " + it.getCount());
        } else {
            ConcurrencyUtils.threadPrintln("counter has been counted down");
        }
    }

}

