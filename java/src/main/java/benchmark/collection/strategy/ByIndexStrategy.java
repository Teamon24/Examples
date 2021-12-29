package benchmark.collection.strategy;

import benchmark.collection.pojo.MethodResult;


import java.util.Collection;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public abstract class ByIndexStrategy<E> extends MethodStrategy<E> {

    private BiConsumer<Collection<E>, Integer> byIndexMethod;
    private Integer index;

    public ByIndexStrategy(final Class collectionClass,
                           final Supplier<Collection<E>> collectionSupplier,
                           final BiConsumer<Collection<E>, Integer> byIndexMethod,
                           final Integer index)
    {
        super(collectionClass, collectionSupplier);
        this.byIndexMethod = byIndexMethod;
        this.index = index;
    }

    @Override
    public void method(Collection<E> collection) {
        this.byIndexMethod.accept(collection, this.index);
    }

    @Override
    public MethodResult createResult(final long executionTime) {
        return new MethodResult(getCollectionType(), getMethodType(), this.index, null, executionTime);
    }

    public Integer getIndex() {
        return index;
    }
}
