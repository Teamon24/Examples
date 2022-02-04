package core.collection.benchmark.core;

import core.collection.benchmark.pojo.MethodResult;
import core.collection.benchmark.pojo.MethodType;
import core.collection.benchmark.utils.TimeMeasureStrategy;
import core.collection.benchmark.strategy.abstrct.MethodStrategy;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public final class MethodTest<E> {

    int testsAmount;
    MethodStrategy<E> methodStrategy;
    TimeMeasureStrategy timeMeasureStrategy;

    public MethodTest(int testsAmount, MethodStrategy<E> methodStrategy) {
        this(testsAmount, methodStrategy, System::nanoTime);
    }

    public List<MethodResult<E>> test(final boolean enableLog, Integer logStep) {
        final List<MethodResult<E>> testResults = new ArrayList<>();
        for (int testNumber = 0; testNumber < this.testsAmount; testNumber++) {
            long executionTime = this.methodStrategy.executeAndGetTime(this.timeMeasureStrategy);

            String collectionType = this.methodStrategy.getCollectionType();
            MethodType methodType = this.methodStrategy.getMethodType();
            MethodResult<E> methodResult = this.methodStrategy.createResult(executionTime);
            testResults.add(methodResult);

            if (enableLog) {
                if (testNumber % logStep == 0) {
                    this.methodStrategy.printTestResult(this.testsAmount, testNumber, collectionType, methodType, executionTime);
                }
            }
        }
        return testResults;
    }
}