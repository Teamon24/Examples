package core.collection.benchmark;

import core.collection.benchmark.pojo.MethodResult;
import core.collection.benchmark.pojo.MethodType;
import core.collection.benchmark.utils.TimeMeasureStrategy;
import core.collection.benchmark.strategy.abstrct.MethodStrategy;

import java.util.ArrayList;
import java.util.List;

public record MethodTest(int testsAmount,
                        MethodStrategy methodStrategy,
                        TimeMeasureStrategy timeMeasureStrategy) {

    public MethodTest(int testsAmount, MethodStrategy methodStrategy) {
        this(testsAmount, methodStrategy, System::nanoTime);
    }

    public List<MethodResult> test(final boolean enableLog, Integer logStep) {
        final List<MethodResult> testResults = new ArrayList<>();
        for (int i = 0; i < testsAmount; i++) {
            long executionTime = methodStrategy.executeAndGetTime(timeMeasureStrategy);

            String collectionType = methodStrategy.getCollectionType();
            MethodType methodType = methodStrategy.getMethodType();
            MethodResult methodResult = methodStrategy.createResult(executionTime);
            testResults.add(methodResult);

            if (enableLog) {
                if (i % logStep == 0) {
                    printTestResult(i, collectionType, methodType, executionTime);
                }
            }
        }
        return testResults;
    }

    private void printTestResult(final int i,
                                 final String listType,
                                 final MethodType methodType,
                                 final long result)
    {
        String messageTemplate = "test: %s, list: %s, method: %s, execution time: %s";
        String message = String.format(messageTemplate, i, listType, methodType, result);
        System.out.println(message);
    }
}