package benchmark.collection.strategy;

import benchmark.collection.pojo.MethodType;


import java.util.Collection;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class GetStrategy<E> extends ByIndexStrategy<E> {

    public GetStrategy(final Class collectionClass,
                       final Supplier<Collection<E>> collectionSupplier,
                       final BiConsumer<Collection<E>, Integer> getMethod,
                       final Integer index)
    {
        super(collectionClass, collectionSupplier, getMethod, index);
    }

    @Override
    public MethodType getMethodType() {
        return MethodType.GET;
    }
}
