package benchmark.collection.strategy;

import benchmark.collection.pojo.MethodType;


import java.util.Collection;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class GetStrategy<E> extends ByIndexStrategy<E> {

    public GetStrategy(final Class collectionClass,
                       final Supplier<Collection<E>> collectionSupplier,
                       final BiConsumer<Collection<E>, Integer> getMethod,
                       final Function<Collection<E>, Integer> indexGetter)
    {
        super(collectionClass, collectionSupplier, getMethod, indexGetter);
    }

    @Override
    public MethodType getMethodType() {
        return MethodType.GET;
    }
}
