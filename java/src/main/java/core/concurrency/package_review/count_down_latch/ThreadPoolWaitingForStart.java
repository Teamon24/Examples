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

import static utils.ConcurrencyUtils.await;
import static utils.ConcurrencyUtils.fixedThreadPool;
import static utils.ConcurrencyUtils.singleThreadPool;
import static utils.ConcurrencyUtils.terminate;
import static utils.ConcurrencyUtils.threadPrintlnTitle;

public class ThreadPoolWaitingForStart {
    public static void main(String[] args) {

        int count = 5;
        int tasksSize = 10;

        CountDownLatch countDownLatch = new CountDownLatch(count);
        List<Runnable> tasks = ListGenerator.create(tasksSize, awaitingTask(countDownLatch));

        ExecutorService executorService = fixedThreadPool(3);
        List<Future<?>> futures = RunnableUtils.submitAll(executorService, tasks);

        ExecutorService singleExecutorService = singleThreadPool("counter");
        singleExecutorService.submit(() -> execCountDown(count, countDownLatch));

        threadPrintlnTitle("AWAITING FOR COUNT DOWN");
        await(countDownLatch);
        threadPrintlnTitle("AWAITING FOR COUNT DOWN IS DONE");
        threadPrintlnTitle("AWAITING FOR JOBS ARE DONE");
        RunnableUtils.waitAll(futures);
        threadPrintlnTitle("JOBS ARE DONE");
        terminate(executorService, singleExecutorService);
    }

    private static Supplier<Runnable> awaitingTask(CountDownLatch countDownLatch) {
        return () -> new Task<>(countDownLatch, ThreadPoolWaitingForStart::awaitLatch);
    }

    public static void awaitLatch(CountDownLatch it) {
        if (it.getCount() != 0) {
            ConcurrencyUtils.threadPrintln("job is awaiting for count down ...");
            ConcurrencyUtils.await(it);
            ConcurrencyUtils.threadPrintln("job completed awaiting.");
        }
    }

    public static void execCountDown(int limit, CountDownLatch countDownLatch) {
        long count;
        for (int i = 0; i < limit; i++) {
            countDownLatch.countDown();
            count = countDownLatch.getCount();
            String message = String.format("is counting down: %s/%s", limit - count, limit);
            ConcurrencyUtils.threadPrintln(message);
            ConcurrencyUtils.sleep(1000L);
        }
    }

}
