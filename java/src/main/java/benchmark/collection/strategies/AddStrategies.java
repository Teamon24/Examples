package benchmark.collection.strategies;

import benchmark.collection.utils.MethodSuppliers;
import benchmark.collection.builder.AddStrategyBuilder;
import benchmark.collection.utils.CollectionSuppliers;
import benchmark.collection.utils.ListMethods;
import benchmark.collection.strategy.MethodStrategy;

import java.util.Collection;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static benchmark.collection.utils.IndexSuppliers.firstIndex;
import static benchmark.collection.utils.IndexSuppliers.lastIndex;
import static benchmark.collection.utils.IndexSuppliers.middleIndex;

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
            .index(firstIndex(), elementSupplier)
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
            .index(middleIndex(), elementSupplier)
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
            .index(lastIndex(), element)
            .build();
    }

    public static <E> MethodStrategy<E> addStrategy(
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
