package core.concurrency.package_review.cyclic_barrier;

import core.concurrency.package_review.Task;
import utils.ConcurrencyUtils;
import utils.RandomUtils;
import utils.RunnableUtils;

import java.util.List;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.function.Supplier;

import static utils.ConcurrencyUtils.fixedThreadPool;
import static utils.ConcurrencyUtils.setThreadName;
import static utils.ConcurrencyUtils.singleThreadPool;
import static utils.ConcurrencyUtils.*;
import static utils.ConcurrencyUtils.terminate;
import static utils.ConcurrencyUtils.threadPrintfln;
import static utils.ConcurrencyUtils.threadPrintln;
import static utils.ListGenerator.create;

/**
 * <p>CyclicBarriers are used in programs in which we have a fixed number of threads that must wait for each org.home.other to reach a common point before continuing execution.
 *
 * <p><strong>The barrier is called cyclic because it can be re-used after the waiting threads are released.</strong>
 */
public class CyclicBarrierDemo {
    public static void main(String[] args) {

        int threadsAmount = 3;
        int barrierThreshold = threadsAmount;
        CyclicBarrier cyclicBarrier = new CyclicBarrier(
            barrierThreshold,
            () -> printOnBehalfOf("CYCLIC-BARRIER", "WAITING THREADS CAN EXECUTE NOW"));

        int tasksSize = threadsAmount * 3;
        ExecutorService executorService = fixedThreadPool(threadsAmount);
        threadPrintfln("barrierThreshold: %s", barrierThreshold);
        threadPrintfln("tasks: %s", tasksSize);

        ExecutorService barrierScanner = singleThreadPool("scanner");
        Future<?> scanningFuture = barrierScanner.submit(scanBarrierTask(cyclicBarrier));

        List<Future<?>> futures =
            RunnableUtils.submitAll(
                executorService,
                create(tasksSize, waitBarrierTask(cyclicBarrier))
            );

        RunnableUtils.waitAll(futures);
        scanningFuture.cancel(true);
        terminate(executorService, barrierScanner);
    }

    private static Runnable scanBarrierTask(CyclicBarrier cyclicBarrier) {
        return new Task<>(cyclicBarrier, CyclicBarrierDemo::scanBarrier);
    }

    private static Supplier<Runnable> waitBarrierTask(CyclicBarrier cyclicBarrier) {
        return () -> new Task<>(cyclicBarrier, CyclicBarrierDemo::awaitBarrier);
    }

    private static void printOnBehalfOf(String onBehalfName, String message) {
        String name = ConcurrencyUtils.getThreadName();
        setThreadName(onBehalfName);
        threadPrintln(message);
        setThreadName(name);
    }

    public static void awaitBarrier(CyclicBarrier cyclicBarrier) {
        if (!cyclicBarrier.isBroken()) {
            threadPrintln("job is awaiting at barrier ...");
            await(cyclicBarrier);
            threadPrintln("job is executing");
            sleep(RandomUtils.random(3, 7));
            threadPrintln("job is done");
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
            threadPrintln(message);
            if (waiting == parties) {
                threadPrintln("scanning is done");
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
