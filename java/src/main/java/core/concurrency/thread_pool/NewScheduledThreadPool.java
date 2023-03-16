package core.concurrency.thread_pool;

import utils.ConcurrencyUtils;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import static core.concurrency.thread_pool.ThreadPoolExamplesUtils.printTask;

public class NewScheduledThreadPool {
    public static void main(String[] args) throws InterruptedException {
        int tasksAmount = 5;
        CountDownLatch lock = new CountDownLatch(tasksAmount);

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(5);
        int period = 1000;
        int initialDelay = 500;
        long taskWorkImitationTime = ThreadPoolExamplesUtils.SLEEP_TIME;

        Runnable task = () -> {
            printTask((int) lock.getCount(), taskWorkImitationTime);
            lock.countDown();
        };

        TimeUnit millis = TimeUnit.MILLISECONDS;

        ScheduledFuture<?> future = executor.scheduleAtFixedRate(task, initialDelay, period, millis);
        int timeout = initialDelay + (period + (int) taskWorkImitationTime) * (tasksAmount) + 2000;

        ConcurrencyUtils.threadPrintln("Count down latch is awaiting for " + timeout);
        lock.await(timeout, millis);
        ConcurrencyUtils.threadPrintln("Count down latch stopped awaiting");


        future.cancel(true);
        ConcurrencyUtils.terminate(1000, executor);
    }
}
