package core.collection.benchmark.core;

import core.collection.benchmark.pojo.MethodResult;
import lombok.AllArgsConstructor;

import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@AllArgsConstructor
public abstract class CollectionTest<E> {

    public abstract List<MethodTest<E>> getMethodsTest();

    protected int testsAmount;
    protected Collection<E> collection;
    protected Supplier<Collection<E>> collectionSupplier;
    protected Supplier<E> newElementSupplier;
    protected Supplier<E> existedElementSupplier;

    public CallableCollectionTest<E> callables() {
        return new CallableCollectionTest<>(this);
    }

    public List<MethodResult<E>> getMethodsResults(boolean enableLog, int logStep) {
        return this.getMethodsTest().stream()
            .map(methodTest -> methodTest.test(enableLog, logStep))
            .flatMap(Collection::stream)
            .collect(Collectors.toList());
    }
}

