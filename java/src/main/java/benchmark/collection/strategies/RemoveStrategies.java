package benchmark.collection.strategies;

import benchmark.collection.utils.MethodSuppliers;
import benchmark.collection.builder.RemoveStrategyBuilder;
import benchmark.collection.utils.CollectionSuppliers;
import benchmark.collection.strategy.MethodStrategy;

import java.util.Collection;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class RemoveStrategies {

    public static <E> MethodStrategy removeFirstStrategy(Collection<E> collection) {
        Supplier<Collection<E>> collectionSupplier = CollectionSuppliers.getNewCollectionSupplier(collection);
        BiConsumer<Collection<E>, Integer> remove = MethodSuppliers.getRemoveByIndex(collection);

        return new RemoveStrategyBuilder<>(collectionSupplier)
            .collectionClass(collection.getClass())
            .removeByIndex(remove)
            .index(1)
            .build();
    }

    public static <E> MethodStrategy removeMiddleStrategy(Collection<E> collection) {
        Supplier<Collection<E>> collectionSupplier = CollectionSuppliers.getNewCollectionSupplier(collection);
        BiConsumer<Collection<E>, Integer> remove = MethodSuppliers.getRemoveByIndex(collection);
        return new RemoveStrategyBuilder<>(collectionSupplier)
            .collectionClass(collection.getClass())
            .removeByIndex(remove)
            .index(collection.size() / 2)
            .build();
    }

    public static <E> MethodStrategy removeLastStrategy(Collection<E> collection) {
        Supplier<Collection<E>> collectionSupplier = CollectionSuppliers.getNewCollectionSupplier(collection);

        BiConsumer<Collection<E>, Integer> remove = MethodSuppliers.getRemoveByIndex(collection);
        return new RemoveStrategyBuilder<>(collectionSupplier)
            .collectionClass(collection.getClass())
            .removeByIndex(remove)
            .index(collection.size() - 1)
            .build();

    }

    public static <E> MethodStrategy removeStrategy(Collection<E> collection, E element)
    {
        Supplier<Collection<E>> collectionSupplier = CollectionSuppliers.getNewCollectionSupplier(collection);
        BiConsumer<Collection<E>, E> remove = MethodSuppliers.getRemoveElement(collection);

        return new RemoveStrategyBuilder<>(collectionSupplier)
            .collectionClass(collection.getClass())
            .removeElement(remove)
            .element(element)
            .build();
    }
}
