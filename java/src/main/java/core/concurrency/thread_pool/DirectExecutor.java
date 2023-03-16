package core.concurrency.thread_pool;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import core.collection.benchmark.utils.Sequence;
import utils.CallableUtils;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicBoolean;

import static core.concurrency.thread_pool.ThreadPoolExamplesUtils.getTasks;

public class DirectExecutor {
    public static void main(String[] args) {
        ListeningExecutorService executorService = MoreExecutors.newDirectExecutorService();
        AtomicBoolean executed = new AtomicBoolean();

        int tasksSize = 5;
        List<Callable<String>> tasks = ThreadPoolExamplesUtils.getTasks(Sequence.first(0).next(it -> it + tasksSize));

        CallableUtils.invokeAll(executorService, tasks);

        assert executed.get();
    }
}
