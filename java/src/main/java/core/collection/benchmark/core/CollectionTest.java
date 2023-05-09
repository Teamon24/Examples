package core.collection.benchmark.core;

import lombok.AllArgsConstructor;

import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

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
}

