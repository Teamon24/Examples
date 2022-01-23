package core.concurrency.thread_pool;

import core.collection.benchmark.utils.TwoStepSequence;
import utils.ConcurrencyUtils;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

import static utils.ConcurrencyUtils.executeAll;
import static utils.ConcurrencyUtils.invokeAll;
import static utils.ConcurrencyUtils.invokeAny;
import static utils.ConcurrencyUtils.getResult;
import static utils.ConcurrencyUtils.submitAll;
import static core.concurrency.thread_pool.ThreadPoolExamplesUtils.*;

public class MethodsOfExecutorService {
    public static void main(String[] args) {

        int taskAmount = 3;

        TwoStepSequence<Integer> taskNumbersRange = TwoStepSequence.first(0).init(it -> it = it + taskAmount);
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(11);

        invokeAll(executor, getTasks("INVOKE_ALL_BLOCKING", taskNumbersRange))
            .forEach(ConcurrencyUtils::getResult);

                                                executeAll(executor, getRunnables("EXECUTE", taskNumbersRange.firstStep()));
        List<Future<String>> submittedFutures = submitAll(executor, getTasks("SUBMIT", taskNumbersRange.firstStep()));
        String resultOfAnyFuture              = invokeAny(executor, getTasks("INVOKE_ANY", taskNumbersRange.firstStep()));
        List<Future<String>> invokedFutures   = invokeAll(executor, getTasks("INVOKE_ALL", taskNumbersRange.firstStep()));

        System.out.println(resultOfAnyFuture);
        submittedFutures.forEach(it -> System.out.println(getResult(it)));
        invokedFutures.forEach(it -> System.out.println(getResult(it)));
    }
}