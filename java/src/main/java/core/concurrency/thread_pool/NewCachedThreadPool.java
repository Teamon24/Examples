package core.concurrency.thread_pool;

import utils.ConcurrencyUtils;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

import static core.concurrency.thread_pool.ThreadPoolExamplesUtils.printPoolAndQueueSizes;
import static core.concurrency.thread_pool.ThreadPoolExamplesUtils.submitEach;
import static utils.PrintUtils.println;

public class NewCachedThreadPool {
    public static void main(String[] args) {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();

        int taskAmount = 3;

        final List<Future<String>> futureTasks = submitEach(executor, taskAmount, ThreadPoolExamplesUtils::task);

        assert taskAmount == executor.getPoolSize();
        assert 0 == executor.getQueue().size();

        printPoolAndQueueSizes(executor, taskAmount);

        futureTasks.forEach(task -> println(ConcurrencyUtils.get(task)));
        ConcurrencyUtils.shutdown(executor, 100);
    }
}
