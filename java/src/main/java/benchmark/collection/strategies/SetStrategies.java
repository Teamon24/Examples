package benchmark.collection.strategies;

import benchmark.collection.utils.CollectionSuppliers;
import benchmark.collection.utils.ListMethods;
import benchmark.collection.strategy.MethodStrategy;
import benchmark.collection.strategy.SetStrategy;

import java.util.Collection;
import java.util.function.Supplier;

public class SetStrategies {

    public static <E> MethodStrategy setFirstStrategy(Collection<E> collection, final E element) {
        Supplier<Collection<E>> collectionSupplier = CollectionSuppliers.getSameCollectionSupplier(collection);

        return new SetStrategy<>(
            collection.getClass(),
            collectionSupplier,
            ListMethods.setter(),
            1,
            element
        );
    }

    public static <E> MethodStrategy setMiddleStrategy(Collection<E> collection, final E element) {
        Supplier<Collection<E>> collectionSupplier = CollectionSuppliers.getSameCollectionSupplier(collection);

        return new SetStrategy<>(
            collection.getClass(),
            collectionSupplier,
            ListMethods.setter(),
            collection.size() / 2,
            element
        );
    }

    public static <E> MethodStrategy setLastStrategy(Collection<E> collection, final E element) {
        Supplier<Collection<E>> collectionSupplier = CollectionSuppliers.getSameCollectionSupplier(collection);

        return new SetStrategy<>(
            collection.getClass(),
            collectionSupplier,
            ListMethods.setter(),
            collection.size() - 1,
            element
        );
    }
}
