package benchmark.collection;

import benchmark.collection.pojo.MethodResult;
import benchmark.collection.pojo.MethodType;
import benchmark.collection.utils.TimeMeasureStrategy;
import benchmark.collection.strategy.MethodStrategy;

import java.util.ArrayList;
import java.util.List;

public class MethodTest {

    private final List<MethodResult> testResults = new ArrayList<>();
    private final int testsAmount;
    private final MethodStrategy methodStrategy;
    private final TimeMeasureStrategy timeMeasureStrategy;

    public MethodTest(int testsAmount,
                      MethodStrategy methodStrategy,
                      TimeMeasureStrategy timeMeasureStrategy)
    {
        this.testsAmount = testsAmount;
        this.methodStrategy = methodStrategy;
        this.timeMeasureStrategy = timeMeasureStrategy;
    }

    public MethodTest(int testsAmount, MethodStrategy methodStrategy) {
        this(testsAmount, methodStrategy, System::currentTimeMillis);
    }

    public List<MethodResult> test(final boolean enableLog, int logStep) {
        for (int i = 0; i < testsAmount; i++) {
            long executionTime = methodStrategy.executeAndGetTime(timeMeasureStrategy);

            String collectionType = methodStrategy.getCollectionType();
            MethodType methodType = methodStrategy.getMethodType();

            MethodResult methodResult = methodStrategy.createResult(executionTime);

            testResults.add(methodResult);

            if (enableLog) {
                if (logStep == 1) {
                    printTestResult(i, collectionType, methodType, executionTime);
                } else if (i % logStep == 0) {
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