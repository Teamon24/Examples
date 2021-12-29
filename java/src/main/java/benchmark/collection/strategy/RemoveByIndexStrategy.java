package benchmark.collection.strategy;

import benchmark.collection.pojo.MethodType;


import java.util.Collection;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class RemoveByIndexStrategy<E> extends ByIndexStrategy<E> {

    public RemoveByIndexStrategy(final Class collectionClass,
                                 final Supplier<Collection<E>> collectionSupplier,
                                 final BiConsumer<Collection<E>, Integer> removeByIndex,
                                 final Integer index)
    {
        super(collectionClass, collectionSupplier, removeByIndex, index);
    }

    @Override
    public MethodType getMethodType() {
        return MethodType.REMOVE;
    }
}
