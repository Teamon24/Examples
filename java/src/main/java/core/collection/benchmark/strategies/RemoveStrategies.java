package core.collection.benchmark.strategies;

import core.collection.benchmark.builder.RemoveStrategyBuilder;
import core.collection.benchmark.strategy.MethodStrategy;
import core.collection.benchmark.utils.IndexSuppliers;
import core.collection.benchmark.utils.MethodSuppliers;

import java.util.Collection;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class RemoveStrategies {

    public static <E> MethodStrategy<E> removeFirstStrategy(
        final Collection<E> collection,
        final Function<Collection<E>, Supplier<Collection<E>>> collectionSupplierFunction)
    {
        Supplier<Collection<E>> collectionSupplier = collectionSupplierFunction.apply(collection);
        BiConsumer<Collection<E>, Integer> remove = MethodSuppliers.getRemoveByIndex(collection);

        return new RemoveStrategyBuilder<>(collectionSupplier)
            .collectionClass(collection.getClass())
            .removeByIndex(remove)
            .index(IndexSuppliers.firstIndex())
            .build();
    }

    public static <E> MethodStrategy<E> removeMiddleStrategy(
        final Collection<E> collection,
        final Function<Collection<E>, Supplier<Collection<E>>> collectionSupplierFunction)
    {
        Supplier<Collection<E>> collectionSupplier = collectionSupplierFunction.apply(collection);
        BiConsumer<Collection<E>, Integer> remove = MethodSuppliers.getRemoveByIndex(collection);
        return new RemoveStrategyBuilder<>(collectionSupplier)
            .collectionClass(collection.getClass())
            .removeByIndex(remove)
            .index(IndexSuppliers.middleIndex())
            .build();
    }

    public static <E> MethodStrategy<E> removeLastStrategy(
        final Collection<E> collection,
        final Function<Collection<E>, Supplier<Collection<E>>> collectionSupplierFunction)
    {
        Supplier<Collection<E>> collectionSupplier = collectionSupplierFunction.apply(collection);

        BiConsumer<Collection<E>, Integer> remove = MethodSuppliers.getRemoveByIndex(collection);
        return new RemoveStrategyBuilder<>(collectionSupplier)
            .collectionClass(collection.getClass())
            .removeByIndex(remove)
            .index(IndexSuppliers.lastIndex())
            .build();

    }

    public static <E> MethodStrategy<E> removeElementStrategy(
        final Collection<E> collection,
        final Function<Collection<E>, Supplier<Collection<E>>> collectionSupplierFunction,
        final Supplier<E> elementSupplier)
    {
        Supplier<Collection<E>> collectionSupplier = collectionSupplierFunction.apply(collection);
        BiConsumer<Collection<E>, E> removeElement = MethodSuppliers.getRemoveElement(collection);

        return new RemoveStrategyBuilder<>(collectionSupplier)
            .collectionClass(collection.getClass())
            .removeElement(removeElement)
            .element(elementSupplier)
            .build();
    }
}
