package core.collection.benchmark.strategy;

import core.collection.benchmark.pojo.MethodType;
import utils.TriConsumer;

import java.util.Collection;
import java.util.function.Function;
import java.util.function.Supplier;

public class SetStrategy<E> extends AddByIndexStrategy<E> {

    public SetStrategy(final Class<?> collectionClass,
                       final Supplier<Collection<E>> collectionSupplier,
                       final TriConsumer<Collection<E>, Integer, E> addByIndex,
                       Function<Collection<E>, Integer> indexSupplier,
                       Supplier<E> elementSupplier)
    {
        super(collectionClass, collectionSupplier, addByIndex, indexSupplier, elementSupplier);
    }

    @Override
    public MethodType getMethodType() {
        return MethodType.SET;
    }
}
