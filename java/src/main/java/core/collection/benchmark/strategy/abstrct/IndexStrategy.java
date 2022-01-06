package core.collection.benchmark.strategy.abstrct;

import core.collection.benchmark.pojo.MethodResult;
import core.collection.benchmark.pojo.MethodType;

import java.util.Collection;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class IndexStrategy<E> extends MethodStrategy<E> {
    protected final Function<Collection<E>, Integer> indexGetter;
    protected Integer index;


    public IndexStrategy(Class<?> collectionClass,
                         Supplier<Collection<E>> collectionSupplier,
                         Function<Collection<E>, Integer> indexGetter) {

        super(collectionClass, collectionSupplier);
        this.indexGetter = indexGetter;
    }

    public long execute(Collection<E> collection) {
        this.index = this.indexGetter.apply(collection);
        return this.timer.count((ignored) -> method(collection, this.index));
    }

    public MethodResult createResult(long executionTime) {
        return new MethodResult(getCollectionType(), getMethodType(), this.index, null, executionTime);
    }

    public abstract void method(Collection<E> collection, Integer index);

    @Override
    public MethodType getMethodType() {
        return null;
    }
}
