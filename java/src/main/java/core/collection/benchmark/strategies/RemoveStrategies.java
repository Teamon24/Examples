package core.collection.benchmark.strategies;

import core.collection.benchmark.builder.RemoveStrategyBuilder;
import core.collection.benchmark.strategy.abstrct.MethodStrategy;

import java.util.Collection;
import java.util.function.Function;
import java.util.function.Supplier;

import static core.collection.benchmark.utils.CollectionSuppliers.newCollection;
import static core.collection.benchmark.utils.IndexSuppliers.*;
import static core.collection.benchmark.utils.MethodSuppliers.*;

public final class RemoveStrategies {

    public static <E> MethodStrategy<E> removeFirstStrategy(final Collection<E> collection) {
        return removeFirstStrategy(collection, newCollection(collection));
    }

    public static <E> MethodStrategy<E> removeMiddleStrategy(final Collection<E> collection) {
        return removeMiddleStrategy(collection, newCollection(collection));
    }

    public static <E> MethodStrategy<E> removeLastStrategy(final Collection<E> collection) {
        return removeLastStrategy(collection, newCollection(collection));
    }

    public static <E> MethodStrategy<E> removeFirstStrategy(
        final Collection<E> collection,
        final Supplier<Collection<E>> collectionSupplier)
    {
        return removeByIndexStrategy(collection, collectionSupplier, firstIndex());
    }

    public static <E> MethodStrategy<E> removeMiddleStrategy(final Collection<E> collection,
                                                             final Supplier<Collection<E>> collectionSupplier)
    {
        return removeByIndexStrategy(collection, collectionSupplier, middleIndex());
    }

    public static <E> MethodStrategy<E> removeLastStrategy(
        final Collection<E> collection,
        final Supplier<Collection<E>> collectionSupplier)
    {
        return removeByIndexStrategy(collection, collectionSupplier, lastIndex());
    }

    public static <E> MethodStrategy<E> removeByIndexStrategy(final Collection<E> collection,
                                                              final Supplier<Collection<E>> collectionSupplier,
                                                              final Function<Collection<E>, Integer> getIndex)
    {
        return new RemoveStrategyBuilder<>(collectionSupplier)
            .collectionClass(collection.getClass())
            .removeByIndex(getRemoveByIndex(collection))
            .index(getIndex)
            .build();
    }

    public static <E> MethodStrategy<E> removeElementStrategy(final Collection<E> collection,
                                                              final Supplier<E> removeElementSupplier)
    {
        return new RemoveStrategyBuilder<>(newCollection(collection))
            .collectionClass(collection.getClass())
            .removeElement(getRemoveElement(collection))
            .element(removeElementSupplier)
            .build();
    }
}
