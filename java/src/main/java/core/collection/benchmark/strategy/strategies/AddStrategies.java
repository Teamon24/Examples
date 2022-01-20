package core.collection.benchmark.strategy.strategies;

import core.collection.benchmark.builder.AddStrategyBuilder;
import core.collection.benchmark.strategy.abstrct.MethodStrategy;
import core.collection.benchmark.utils.IndexSuppliers;
import core.collection.benchmark.utils.ListMethods;

import java.util.Collection;
import java.util.function.Function;
import java.util.function.Supplier;

import static core.collection.benchmark.utils.CollectionSuppliers.newCollection;

public final class AddStrategies {

    public static <E> MethodStrategy<E> addFirstStrategy(
        final Collection<E> collection,
        final Supplier<E> elementSupplier
    ) {
        return addFirstStrategy(collection, newCollection(collection), elementSupplier);
    }

    public static <E> MethodStrategy<E> addMiddleStrategy(
        final Collection<E> collection,
        final Supplier<E> elementSupplier
    ) {
        return addMiddleStrategy(collection, newCollection(collection), elementSupplier);
    }

    public static <E> MethodStrategy<E> addLastStrategy(
        final Collection<E> collection,
        final Supplier<E> elementSupplier
    ) {
        return addLastStrategy(collection, newCollection(collection), elementSupplier);
    }

    public static <E> MethodStrategy<E> addFirstStrategy(
        final Collection<E> collection,
        final Supplier<Collection<E>> collectionSupplier,
        final Supplier<E> elementSupplier
    ) {
        return addByIndexStrategy(collection, collectionSupplier, IndexSuppliers.firstIndex(), elementSupplier);
    }

    public static <E> MethodStrategy<E> addMiddleStrategy(
        final Collection<E> collection,
        final Supplier<Collection<E>> collectionSupplier,
        final Supplier<E> elementSupplier
    ) {
        return addByIndexStrategy(collection, collectionSupplier, IndexSuppliers.middleIndex(), elementSupplier);
    }

    public static <E> MethodStrategy<E> addLastStrategy(
        final Collection<E> collection,
        final Supplier<Collection<E>> collectionSupplier,
        final Supplier<E> elementSupplier
    ) {
        return addByIndexStrategy(collection, collectionSupplier, IndexSuppliers.lastIndex(), elementSupplier);
    }

    public static <E> MethodStrategy<E> addByIndexStrategy(
        final Collection<E> collection,
        final Supplier<Collection<E>> collectionSupplier,
        final Function<Collection<E>, Integer> indexSupplier,
        final Supplier<E> elementSupplier
    ) {
        return new AddStrategyBuilder<>(collectionSupplier)
            .collectionClass(collection.getClass())
            .addByIndex(ListMethods.addByIndex())
            .index(indexSupplier)
            .element(elementSupplier)
            .build();
    }

    public static <E> MethodStrategy<E> addElementStrategy(
        final Collection<E> collection,
        final Supplier<Collection<E>> collectionSupplier,
        final Supplier<E> elementSupplier
    ) {
        return new AddStrategyBuilder<>(collectionSupplier)
            .collectionClass(collection.getClass())
            .addElement(Collection::add)
            .element(elementSupplier)
            .build();
    }
}