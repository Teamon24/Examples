package benchmark.collection.strategy;

import benchmark.collection.pojo.MethodType;
import benchmark.collection.utils.TriConsumer;

import java.util.Collection;
import java.util.function.Supplier;

public class SetStrategy<E> extends AddByIndexStrategy<E> {

    public SetStrategy(final Class collectionClass,
                       final Supplier<Collection<E>> collectionSupplier,
                       final TriConsumer<Collection<E>, Integer, E> addByIndex,
                       final Integer index,
                       final E element)
    {
        super(collectionClass, collectionSupplier, addByIndex, index, element);
    }

    @Override
    public MethodType getMethodType() {
        return MethodType.SET;
    }
}
