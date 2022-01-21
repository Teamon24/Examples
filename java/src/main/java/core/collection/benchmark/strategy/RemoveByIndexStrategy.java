package core.collection.benchmark.strategy;

import core.collection.benchmark.pojo.MethodType;
import core.collection.benchmark.strategy.abstrct.ByIndexStrategy;

import java.util.Collection;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class RemoveByIndexStrategy<E> extends ByIndexStrategy<E> {

    public RemoveByIndexStrategy(final Class<?> collectionClass,
                                 final Supplier<Collection<E>> collectionSupplier,
                                 final BiConsumer<Collection<E>, Integer> removeByIndex,
                                 final Function<Collection<E>, Integer> indexSupplier)
    {
        super(collectionClass, collectionSupplier, removeByIndex, indexSupplier);
    }

    @Override
    public MethodType getMethodType() {
        return MethodType.REMOVE_BY_INDEX;
    }
}
