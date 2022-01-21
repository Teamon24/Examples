package core.collection.benchmark;

import core.collection.benchmark.pojo.MethodResult;
import core.collection.benchmark.utils.IndexSuppliers;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.Function;
import java.util.stream.Collectors;

public record CallableCollectionTest<E>(CollectionTest<E> collectionTest) {

    public List<Callable<List<MethodResult<E>>>> getTests(boolean enableLog, final int logStep) {
        Function<Collection<E>, Integer> indexSupplier = IndexSuppliers.getThreeIndexes();
        List<MethodTest<E>> methodTests = this.collectionTest.getMethodTests(indexSupplier);
        return callables(methodTests, enableLog, logStep);
    }

    public List<Callable<List<MethodResult<E>>>> getRandomIndexTests(boolean enableLog, final int logStep) {
        Function<Collection<E>, Integer> randomIndex = IndexSuppliers.getRandomIndex();
        List<MethodTest<E>> methodTests = this.collectionTest.getListMethodTests(randomIndex);
        return callables(methodTests, enableLog, logStep);
    }

    private List<Callable<List<MethodResult<E>>>> callables(
        List<MethodTest<E>> methodTests,
        boolean enableLog,
        int logStep
    ) {
        return methodTests
            .stream()
            .map(methodTest -> callable(methodTest, enableLog, logStep))
            .collect(Collectors.toList());
    }

    private Callable<List<MethodResult<E>>> callable(MethodTest<E> methodTest, boolean enableLog, int logStep) {
        return () -> methodTest.test(enableLog, logStep);
    }
}
