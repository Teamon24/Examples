package core.concurrency.thread_pool;

import com.google.common.util.concurrent.MoreExecutors;
import utils.ConcurrencyUtils;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

import static core.concurrency.thread_pool.ThreadPoolExamplesUtils.printPoolAndQueueSizes;
import static core.concurrency.thread_pool.ThreadPoolExamplesUtils.submitEach;
import static utils.PrintUtils.println;

public class NewFixedThreadPool {

    static int poolSize = 2;

    public static void main(String[] args) {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(poolSize);

        Duration timeout = Duration.of(1000, ChronoUnit.MILLIS);
        ExecutorService exitingExecutor = MoreExecutors.getExitingExecutorService(executor, timeout);

        int taskAmount = 6;
        final List<Future<String>> futureTasks =
            submitEach(
                exitingExecutor,
                taskAmount,
                ThreadPoolExamplesUtils::task);

        assert poolSize == executor.getPoolSize();
        assert taskAmount - poolSize == executor.getQueue().size();

        printPoolAndQueueSizes(executor, taskAmount);

        futureTasks.forEach(task -> println(ConcurrencyUtils.get(task)));
    }
}
