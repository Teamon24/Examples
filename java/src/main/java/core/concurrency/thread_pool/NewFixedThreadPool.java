package core.concurrency.thread_pool;

import com.google.common.util.concurrent.MoreExecutors;
import core.concurrency.ConcurrencyUtils;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

import static core.concurrency.thread_pool.ThreadPoolExamplesUtils.*;
import static core.concurrency.thread_pool.ThreadPoolExamplesUtils.submitEach;

public class NewFixedThreadPool {
    static int poolSize = 2;
    public static void main(String[] args) {
        ThreadPoolExecutor executor =
            (ThreadPoolExecutor) Executors.newFixedThreadPool(poolSize);

        int taskAmount = 3;
        ExecutorService exitingExecutor =
            MoreExecutors.getExitingExecutorService(executor, Duration.of(1000, ChronoUnit.MILLIS));

        final List<Future<String>> futureTasks =
            submitEach(
                exitingExecutor,
                taskAmount,
                ThreadPoolExamplesUtils::callable);

        //two tasks will get threads and one will stay in queue
        assert poolSize == executor.getPoolSize();
        assert taskAmount - poolSize == executor.getQueue().size();

        print(executor, taskAmount);

        futureTasks.forEach(task -> System.out.println(ConcurrencyUtils.getResult(task)));
        ConcurrencyUtils.sleep(10000);
    }
}
