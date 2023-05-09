package core.concurrency.thread_pool;

import utils.ConcurrencyUtils;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

import static core.concurrency.thread_pool.ThreadPoolExamplesUtils.printPoolAndQueueSizes;
import static core.concurrency.thread_pool.ThreadPoolExamplesUtils.submitAll;

public class NewCachedThreadPool {
    public static void main(String[] args) {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();

        int taskAmount = 3;

        final List<Future<String>> futureTasks = submitAll(executor, taskAmount, ThreadPoolExamplesUtils::task);

        assert taskAmount == executor.getPoolSize();
        assert 0 == executor.getQueue().size();

        printPoolAndQueueSizes(executor, taskAmount);

        futureTasks.forEach(task -> System.out.println(ConcurrencyUtils.get(task)));
        ConcurrencyUtils.terminate(100, executor);
    }
}
