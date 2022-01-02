package core.collection.benchmark.strategies;

import core.collection.benchmark.utils.MethodSuppliers;
import core.collection.benchmark.builder.AddStrategyBuilder;
import core.collection.benchmark.utils.ListMethods;
import core.collection.benchmark.strategy.MethodStrategy;
import core.collection.benchmark.utils.IndexSuppliers;

import java.util.Collection;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class AddStrategies {
    public static <E> MethodStrategy<E> addFirstStrategy(
        final Collection<E> collection,
        final Function<Collection<E>, Supplier<Collection<E>>> collectionSupplierGetter,
        final Supplier<E> elementSupplier)
    {
        Supplier<Collection<E>> collectionSupplier = collectionSupplierGetter.apply(collection);
        return new AddStrategyBuilder<>(collectionSupplier)
            .collectionClass(collection.getClass())
            .addByIndex(ListMethods.addByIndex())
            .index(IndexSuppliers.firstIndex(), elementSupplier)
            .build();
    }

    public static <E> MethodStrategy<E> addMiddleStrategy(
        final Collection<E> collection,
        final Function<Collection<E>, Supplier<Collection<E>>> collectionSupplierGetter,
        final Supplier<E> elementSupplier)
    {
        Supplier<Collection<E>> collectionSupplier = collectionSupplierGetter.apply(collection);

        return new AddStrategyBuilder<>(collectionSupplier)
            .collectionClass(collection.getClass())
            .addByIndex(ListMethods.addByIndex())
            .index(IndexSuppliers.middleIndex(), elementSupplier)
            .build();
    }

    public static <E> MethodStrategy<E> addLastStrategy(
        final Collection<E> collection,
        final Function<Collection<E>, Supplier<Collection<E>>> collectionSupplierGetter,
        final Supplier<E> element)
    {
        Supplier<Collection<E>> collectionSupplier = collectionSupplierGetter.apply(collection);
        return new AddStrategyBuilder<>(collectionSupplier)
            .collectionClass(collection.getClass())
            .addByIndex(ListMethods.addByIndex())
            .index(IndexSuppliers.lastIndex(), element)
            .build();
    }

    public static <E> MethodStrategy<E> addElementStrategy(
        final Collection<E> collection,
        final Function<Collection<E>, Supplier<Collection<E>>> collectionSupplierGetter,
        final Supplier<E> elementSupplier)
    {
        Supplier<Collection<E>> collectionSupplier = collectionSupplierGetter.apply(collection);
        BiConsumer<Collection<E>, E> addElement = MethodSuppliers.getAddElement(collection);

        return new AddStrategyBuilder<>(collectionSupplier)
            .collectionClass(collection.getClass())
            .addElement(addElement)
            .element(elementSupplier)
            .build();
    }
}
