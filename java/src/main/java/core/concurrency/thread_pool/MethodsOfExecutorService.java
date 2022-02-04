package core.concurrency.thread_pool;

import core.collection.benchmark.utils.TwoStepSequence;
import utils.ConcurrencyUtils;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

import static core.concurrency.thread_pool.ThreadPoolExamplesUtils.getRunnables;
import static core.concurrency.thread_pool.ThreadPoolExamplesUtils.getTasks;
import static utils.ConcurrencyUtils.executeAll;
import static utils.ConcurrencyUtils.get;
import static utils.ConcurrencyUtils.invokeAll;
import static utils.ConcurrencyUtils.invokeAny;
import static utils.ConcurrencyUtils.safePrintln;
import static utils.ConcurrencyUtils.submitAll;
import static utils.PrintUtils.println;

public class MethodsOfExecutorService {
    public static void main(String[] args) {

        int taskAmount = 3;

        TwoStepSequence<Integer> taskNumbersRange = TwoStepSequence.first(0).init(it -> it = it + taskAmount);
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(11);

        invokeAll(executor, getTasks("INVOKE_ALL_BLOCKING", taskNumbersRange)).forEach(ConcurrencyUtils::get);

                                                executeAll(executor, getRunnables("EXECUTE", taskNumbersRange.firstStep()));
        List<Future<String>> submittedFutures = submitAll(executor, getTasks("SUBMIT", taskNumbersRange.firstStep()));
        String resultOfAnyFuture              = invokeAny(executor, getTasks("INVOKE_ANY", taskNumbersRange.firstStep()));
        List<Future<String>> invokedFutures   = invokeAll(executor, getTasks("INVOKE_ALL", taskNumbersRange.firstStep()));

        println(resultOfAnyFuture);
        submittedFutures.forEach(it -> safePrintln(get(it)));
        invokedFutures.forEach(it -> println(get(it)));
        ConcurrencyUtils.shutdown(executor, 300);
    }
}