package core.concurrency.thread_pool;

import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import core.collection.benchmark.utils.TwoStepSequence;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicBoolean;

import static utils.ConcurrencyUtils.invokeAll;
import static core.concurrency.thread_pool.ThreadPoolExamplesUtils.*;

public class DirectExecutor {
    public static void main(String[] args) {
        ListeningExecutorService executorService = MoreExecutors.newDirectExecutorService();
        AtomicBoolean executed = new AtomicBoolean();

        List<Callable<String>> tasks = getTasks(TwoStepSequence.first(0).init(it -> it + 5));

        invokeAll(executorService, tasks);

        assert executed.get();
    }
}
