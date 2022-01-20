package core.collection.benchmark.strategy.strategies;

import core.collection.benchmark.builder.RemoveStrategyBuilder;
import core.collection.benchmark.strategy.abstrct.MethodStrategy;
import core.collection.benchmark.utils.ListMethods;

import java.util.Collection;
import java.util.function.Function;
import java.util.function.Supplier;

import static core.collection.benchmark.utils.CollectionSuppliers.newCollection;
import static core.collection.benchmark.utils.IndexSuppliers.firstIndex;
import static core.collection.benchmark.utils.IndexSuppliers.lastIndex;
import static core.collection.benchmark.utils.IndexSuppliers.middleIndex;

public final class RemoveStrategies {

    public static <E> MethodStrategy<E> removeFirstStrategy(final Collection<E> collection) {
        return removeByIndexStrategy(collection, newCollection(collection), firstIndex());
    }

    public static <E> MethodStrategy<E> removeMiddleStrategy(final Collection<E> collection) {
        return removeByIndexStrategy(collection, newCollection(collection), middleIndex());
    }

    public static <E> MethodStrategy<E> removeLastStrategy(final Collection<E> collection) {
        return removeByIndexStrategy(collection, newCollection(collection), lastIndex());
    }

    public static <E> MethodStrategy<E> removeFirstStrategy(
        final Collection<E> collection,
        final Supplier<Collection<E>> collectionSupplier
    ) {
        return removeByIndexStrategy(collection, collectionSupplier, firstIndex());
    }

    public static <E> MethodStrategy<E> removeMiddleStrategy(
        final Collection<E> collection,
        final Supplier<Collection<E>> collectionSupplier
    ) {
        return removeByIndexStrategy(collection, collectionSupplier, middleIndex());
    }

    public static <E> MethodStrategy<E> removeLastStrategy(
        final Collection<E> collection,
        final Supplier<Collection<E>> collectionSupplier
    ) {
        return removeByIndexStrategy(collection, collectionSupplier, lastIndex());
    }

    public static <E> MethodStrategy<E> removeByIndexStrategy(
        final Collection<E> collection,
        final Function<Collection<E>, Integer> indexGetter
    ) {
        return removeByIndexStrategy(collection, newCollection(collection), indexGetter);
    }

    public static <E> MethodStrategy<E> removeByIndexStrategy(
        final Collection<E> collection,
        final Supplier<Collection<E>> collectionSupplier,
        final Function<Collection<E>, Integer> indexGetter
    ) {
        return new RemoveStrategyBuilder<>(collectionSupplier)
            .collectionClass(collection.getClass())
            .removeByIndex(ListMethods.removeByIndex())
            .index(indexGetter)
            .build();
    }

    public static <E> MethodStrategy<E> removeElementStrategy(
        final Collection<E> collection,
        final Supplier<E> removableElementSupplier
    ) {
        return removeElementStrategy(collection, newCollection(collection), removableElementSupplier);
    }

    public static <E> MethodStrategy<E> removeElementStrategy(
        final Collection<E> collection,
        final Supplier<Collection<E>> collectionSupplier,
        final Supplier<E> removableElementSupplier
    ) {
        return new RemoveStrategyBuilder<>(collectionSupplier)
            .collectionClass(collection.getClass())
            .removeElement(Collection::remove)
            .element(removableElementSupplier)
            .build();
    }
}
