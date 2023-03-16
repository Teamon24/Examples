package core.concurrency.package_review;

import utils.ConcurrencyUtils;
import utils.RandomUtils;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.function.Consumer;

public class Task<T> implements Runnable {

    private T lock;
    private Consumer<T> lockAction;

    public Task(T lock, Consumer<T> lockAction) {
        this.lock = lock;
        this.lockAction = lockAction;
    }

    @Override
    public void run() {
        try {
            this.lockAction.accept(this.lock);
        } catch (Exception e) {
            e.printStackTrace();
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

    public static void awaitLatch(CountDownLatch it) {
        if (it.getCount() != 0) {
            ConcurrencyUtils.threadPrintln("job is awaiting for count down ...");
            ConcurrencyUtils.await(it);
            ConcurrencyUtils.threadPrintln("job completed awaiting.");
        }
    }

    public static void awaitBarrier(CyclicBarrier cyclicBarrier) {
        if (!cyclicBarrier.isBroken()) {
            ConcurrencyUtils.threadPrintln("job is awaiting at barrier ...");
            ConcurrencyUtils.await(cyclicBarrier);
            ConcurrencyUtils.threadPrintln("job is executing");
            ConcurrencyUtils.sleep(RandomUtils.random(3, 7));
            ConcurrencyUtils.threadPrintln("job is done");
        } else {
            throw new RuntimeException("Cyclic Barrier was broken.");
        }
    }

    public static void scanBarrier(CyclicBarrier cyclicBarrier) {
        int waiting;
        int parties;
        while (true) {
            waiting = cyclicBarrier.getNumberWaiting();
            parties = cyclicBarrier.getParties();
            String message = String.format("threads in barrier: %s/%s", waiting, parties);
            ConcurrencyUtils.threadPrintln(message);
            if (waiting == parties) {
                ConcurrencyUtils.threadPrintln("scanning is done");
                break;
            }
            try {
                Thread.sleep(250L);
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}