package benchmark.collection.strategies;

import benchmark.collection.builder.RemoveStrategyBuilder;
import benchmark.collection.strategy.MethodStrategy;
import benchmark.collection.utils.IndexSuppliers;
import benchmark.collection.utils.MethodSuppliers;

import java.util.Collection;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static benchmark.collection.utils.IndexSuppliers.firstIndex;
import static benchmark.collection.utils.IndexSuppliers.middleIndex;

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
            .index(firstIndex())
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
            .index(middleIndex())
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
