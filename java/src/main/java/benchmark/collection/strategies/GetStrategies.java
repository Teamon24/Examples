package benchmark.collection.strategies;

import benchmark.collection.utils.CollectionSuppliers;
import benchmark.collection.utils.ListMethods;
import benchmark.collection.strategy.GetStrategy;
import benchmark.collection.strategy.MethodStrategy;

import java.util.Collection;
import java.util.function.Supplier;

public class GetStrategies {
    public static <E> MethodStrategy<E> getFirstStrategy(Collection<E> collection) {
        Supplier<Collection<E>> collectionSupplier = CollectionSuppliers.getSameCollectionSupplier(collection);

        return new GetStrategy<>(
            collection.getClass(),
            collectionSupplier,
            ListMethods.getByIndex(),
            1
        );
    }

    public static <E> MethodStrategy<E> getMiddleStrategy(Collection<E> collection) {
        Supplier<Collection<E>> collectionSupplier = CollectionSuppliers.getSameCollectionSupplier(collection);

        return new GetStrategy<E>(
            collection.getClass(),
            collectionSupplier,
            ListMethods.getByIndex(),
            collection.size() / 2
        );
    }

    public static <E> MethodStrategy<E> getLastStrategy(Collection<E> collection) {
        Supplier<Collection<E>> collectionSupplier = CollectionSuppliers.getSameCollectionSupplier(collection);

        return new GetStrategy<>(
            collection.getClass(),
            collectionSupplier,
            ListMethods.getByIndex(),
            collection.size() - 1
        );
    }
}
