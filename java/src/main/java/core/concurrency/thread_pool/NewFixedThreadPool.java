package core.concurrency.thread_pool;

import utils.CallableUtils;
import utils.ConcurrencyUtils;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

import static core.concurrency.thread_pool.ThreadPoolExamplesUtils.printPoolAndQueueSizes;
import static core.concurrency.thread_pool.ThreadPoolExamplesUtils.submitAll;

public class NewFixedThreadPool {

    static int poolSize = 2;

    public static void main(String[] args) {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(poolSize);

        int taskAmount = 6;
        final List<Future<String>> futureTasks =
            submitAll(
                executor,
                taskAmount,
                ThreadPoolExamplesUtils::task);

        assert poolSize == executor.getPoolSize();
        assert taskAmount - poolSize == executor.getQueue().size();

        printPoolAndQueueSizes(executor, taskAmount);

        CallableUtils.getAll(futureTasks);
        ConcurrencyUtils.terminate(executor);
    }
}
