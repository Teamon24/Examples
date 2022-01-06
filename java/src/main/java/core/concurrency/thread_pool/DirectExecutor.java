package core.concurrency.thread_pool;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import core.concurrency.ConcurrencyUtils;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicBoolean;

public class DirectExecutor {
    public static void main(String[] args) {
        ListeningExecutorService executorService = MoreExecutors.newDirectExecutorService();
        AtomicBoolean executed = new AtomicBoolean();

        List<Callable<String>> tasks = ThreadPoolExamplesUtils.createTasks(10, 1000);
        ThreadPoolExamplesUtils.invokeAll(executorService, tasks);

        assert executed.get();
        System.out.println("Task was executed in: " + ConcurrencyUtils.threadName());
    }
}
