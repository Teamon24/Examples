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
        singleThreadExecutor.submit(() -> Task.scanCountDown(count, countDownLatch));
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
        return () -> new Task<>(countDownLatch, Task::countDown);
    }
}

