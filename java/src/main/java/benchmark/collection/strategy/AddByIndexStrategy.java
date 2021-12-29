package benchmark.collection.strategy;

import benchmark.collection.pojo.MethodResult;
import benchmark.collection.pojo.MethodType;
import benchmark.collection.utils.TriConsumer;

import java.util.Collection;
import java.util.function.Supplier;

public class AddByIndexStrategy<E> extends MethodStrategy<E> {

    private TriConsumer<Collection<E>, Integer, E> addByIndex;
    private Integer index;
    private E element;

    public AddByIndexStrategy(Class collectionClass,
                              Supplier<Collection<E>> collectionSupplier,
                              TriConsumer<Collection<E>, Integer, E> addByIndex,
                              Integer index,
                              E element)
    {
        super(collectionClass, collectionSupplier);
        this.addByIndex = addByIndex;
        this.index = index;
        this.element = element;
    }

    @Override
    public void method(Collection<E> collection) {
        addByIndex.accept(collection, index, element);
    }

    @Override
    public MethodResult createResult(final long executionTime) {
        return new MethodResult(getCollectionType(), getMethodType(), index, element, executionTime);
    }

    @Override
    public MethodType getMethodType() {
        return MethodType.ADD;
    }
}
