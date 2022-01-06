package core.concurrency.thread_pool;

import com.google.common.util.concurrent.MoreExecutors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ExitingExecutorService {
    public static void main(String[] args) {
        int threads = 5;
        int terminationTimeout = 5000;

        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(threads);
        ExecutorService executorService =
            MoreExecutors.getExitingExecutorService(executor, terminationTimeout, TimeUnit.MILLISECONDS);

        executorService.submit(ThreadPoolExamplesUtils.infiniteLoop());
    }
}
