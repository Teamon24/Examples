package core.concurrency.package_review.cyclic_barrier;

import core.concurrency.package_review.Task;
import utils.ConcurrencyUtils;
import utils.RunnableUtils;

import java.util.List;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.function.Supplier;

import static utils.ConcurrencyUtils.fixedThreadPool;
import static utils.ConcurrencyUtils.setThreadName;
import static utils.ConcurrencyUtils.singleThreadPool;
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
        return new Task<>(cyclicBarrier, Task::scanBarrier);
    }

    private static Supplier<Runnable> waitBarrierTask(CyclicBarrier cyclicBarrier) {
        return () -> new Task<>(cyclicBarrier, Task::awaitBarrier);
    }

    private static void printOnBehalfOf(String onBehalfName, String message) {
        String name = ConcurrencyUtils.getThreadName();
        setThreadName(onBehalfName);
        threadPrintln(message);
        setThreadName(name);
    }

}
