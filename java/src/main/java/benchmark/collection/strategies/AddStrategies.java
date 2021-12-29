package benchmark.collection.strategies;

import benchmark.collection.utils.MethodSuppliers;
import benchmark.collection.builder.AddStrategyBuilder;
import benchmark.collection.utils.CollectionSuppliers;
import benchmark.collection.utils.ListMethods;
import benchmark.collection.strategy.MethodStrategy;

import java.util.Collection;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class AddStrategies {
    public static <E> MethodStrategy<E> addFirstStrategy(Collection<E> collection, final E element) {
        Supplier<Collection<E>> collectionSupplier = CollectionSuppliers.getSameCollectionSupplier(collection);

        return new AddStrategyBuilder<>(collectionSupplier)
            .collectionClass(collection.getClass())
            .addByIndex(ListMethods.addByIndex())
            .index(1, element)
            .build();
    }

    public static <E> MethodStrategy<E> addMiddleStrategy(Collection<E> collection, final E element) {
        Supplier<Collection<E>> collectionSupplier = CollectionSuppliers.getSameCollectionSupplier(collection);

        return new AddStrategyBuilder<>(collectionSupplier)
            .collectionClass(collection.getClass())
            .addByIndex(ListMethods.addByIndex())
            .index(collection.size() / 2, element)
            .build();
    }

    public static <E> MethodStrategy<E> addLastStrategy(Collection<E> collection, final E element) {
        Supplier<Collection<E>> collectionSupplier = CollectionSuppliers.getSameCollectionSupplier(collection);
        return new AddStrategyBuilder<>(collectionSupplier)
            .collectionClass(collection.getClass())
            .addByIndex(ListMethods.addByIndex())
            .index(collection.size() - 1, element)
            .build();
    }

    public static <E> MethodStrategy<E> addStrategy(Collection<E> collection,
                                                 E element)
    {
        Supplier<Collection<E>> collectionSupplier = CollectionSuppliers.getSameCollectionSupplier(collection);
        BiConsumer<Collection<E>, E> addElement = MethodSuppliers.getAddElement(collection);

        return new AddStrategyBuilder<>(collectionSupplier)
            .collectionClass(collection.getClass())
            .addElement(addElement)
            .element(element)
            .build();
    }
}
