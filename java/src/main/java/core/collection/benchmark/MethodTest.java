package core.collection.benchmark;

import core.collection.benchmark.pojo.MethodResult;
import core.collection.benchmark.pojo.MethodType;
import core.collection.benchmark.utils.TimeMeasureStrategy;
import core.collection.benchmark.strategy.abstrct.MethodStrategy;

import java.util.ArrayList;
import java.util.List;

public record MethodTest(int testsAmount, MethodStrategy methodStrategy, TimeMeasureStrategy timeMeasureStrategy) {

    public MethodTest(int testsAmount, MethodStrategy methodStrategy) {
        this(testsAmount, methodStrategy, System::nanoTime);
    }

    public <E> List<MethodResult<E>> test(final boolean enableLog, Integer logStep) {
        final List<MethodResult<E>> testResults = new ArrayList<>();
        for (int testNumber = 0; testNumber < testsAmount; testNumber++) {
            long executionTime = methodStrategy.executeAndGetTime(timeMeasureStrategy);

            String collectionType = methodStrategy.getCollectionType();
            MethodType methodType = methodStrategy.getMethodType();
            MethodResult<E> methodResult = methodStrategy.createResult(executionTime);
            testResults.add(methodResult);

            if (enableLog) {
                if (testNumber % logStep == 0) {
                    methodStrategy.printTestResult(testsAmount, testNumber, collectionType, methodType, executionTime);
                }
            }
        }
        return testResults;
    }
}