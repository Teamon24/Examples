package core.concurrency.package_review.count_down_latch;

import core.concurrency.package_review.Task;
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
        singleExecutorService.submit(() -> Task.execCountDown(count, countDownLatch));

        threadPrintlnTitle("AWAITING FOR COUNT DOWN");
        await(countDownLatch);
        threadPrintlnTitle("AWAITING FOR COUNT DOWN IS DONE");
        threadPrintlnTitle("AWAITING FOR JOBS ARE DONE");
        RunnableUtils.waitAll(futures);
        threadPrintlnTitle("JOBS ARE DONE");
        terminate(executorService, singleExecutorService);
    }

    private static Supplier<Runnable> awaitingTask(CountDownLatch countDownLatch) {
        return () -> new Task<>(countDownLatch, Task::awaitLatch);
    }
}
