package core.collection.benchmark.core;

import core.collection.benchmark.utils.MethodsTestsTasks;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class CallableCollectionTest<E> {

    private CollectionTest<E> collectionTest;

    public List<MethodsTestsTasks<E>> getMethodsTests(boolean enableLog, final int logStep) {
        return this.collectionTest.getMethodsTest()
            .stream()
            .map(methodTest -> callableOf(methodTest, enableLog, logStep))
            .collect(Collectors.toList());
    }

    private MethodsTestsTasks<E> callableOf(MethodTest<E> methodTest, boolean enableLog, int logStep) {
        return () -> methodTest.test(enableLog, logStep);
    }
}
