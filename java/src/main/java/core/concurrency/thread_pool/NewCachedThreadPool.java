package core.concurrency.thread_pool;

import core.concurrency.ConcurrencyUtils;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

import static core.concurrency.thread_pool.ThreadPoolExamplesUtils.*;
import static core.concurrency.thread_pool.ThreadPoolExamplesUtils.submitEach;

public class NewCachedThreadPool {
    public static void main(String[] args) {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();

        int taskAmount = 3;

        final List<Future<String>> futureTasks = submitEach(executor, taskAmount, ThreadPoolExamplesUtils::callable);

        assert taskAmount == executor.getPoolSize();
        assert 0 == executor.getQueue().size();

        print(executor, taskAmount);

        futureTasks.forEach(task -> System.out.println(ConcurrencyUtils.getResult(task)));
    }
}
