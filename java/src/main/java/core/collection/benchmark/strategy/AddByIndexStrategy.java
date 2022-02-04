package core.collection.benchmark.strategy;

import core.collection.benchmark.pojo.MethodType;
import core.collection.benchmark.strategy.abstrct.IndexAndElementStrategy;
import utils.TriConsumer;

import java.util.Collection;
import java.util.function.Function;
import java.util.function.Supplier;

public class AddByIndexStrategy<E> extends IndexAndElementStrategy<E> {

    private final TriConsumer<Collection<E>, Integer, E> addByIndex;

    public AddByIndexStrategy(Class<?> collectionClass,
                              Supplier<Collection<E>> collectionSupplier,
                              TriConsumer<Collection<E>, Integer, E> addByIndex,
                              Function<Collection<E>, Integer> indexSupplier,
                              Supplier<E> elementSupplier)
    {
        super(collectionClass, collectionSupplier, indexSupplier, elementSupplier);
        this.addByIndex = addByIndex;
    }

    @Override
    public void method(final Collection<E> collection, final Integer index, final E element) {
        this.addByIndex.accept(collection, index, element);
    }

    @Override
    public MethodType getMethodType() {
        return MethodType.ADD_BY_INDEX;
    }
}
