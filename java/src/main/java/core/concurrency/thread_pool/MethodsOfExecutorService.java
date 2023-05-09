package core.concurrency.thread_pool;

import core.collection.benchmark.utils.TwoStepSequence;
import utils.CallableUtils;
import utils.ConcurrencyUtils;
import utils.RunnableUtils;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class MethodsOfExecutorService {
    public static void main(String[] args) {

        int taskAmount = 5;

        TwoStepSequence<Integer> taskNumbersRange = TwoStepSequence.first(0).next(it -> it = it + taskAmount);
        ExecutorService executor = ConcurrencyUtils.fixedThreadPool(11);

        CallableUtils.invokeAll(executor, ThreadPoolExamplesUtils.getTasks("invoke_all_blocking", taskNumbersRange))
            .forEach(ConcurrencyUtils::get);

                                                RunnableUtils.executeAll(executor, ThreadPoolExamplesUtils.getRunnables("execute", taskNumbersRange.firstStep()));
        List<Future<String>> submittedFutures = CallableUtils.submitAll(executor, ThreadPoolExamplesUtils.getTasks("submit", taskNumbersRange.firstStep()));
        List<Future<String>> invokedFutures   = CallableUtils.invokeAll(executor, ThreadPoolExamplesUtils.getTasks("invoke_all", taskNumbersRange.firstStep()));
        String resultOfAnyFuture              = CallableUtils.invokeAny(executor, ThreadPoolExamplesUtils.getTasks("invoke_any", taskNumbersRange.firstStep()));

        ConcurrencyUtils.threadPrintln("InvokeAny result: " + resultOfAnyFuture);

        submittedFutures.forEach(ConcurrencyUtils::get);
        invokedFutures.forEach(ConcurrencyUtils::get);
        ConcurrencyUtils.terminate(executor);
    }
}