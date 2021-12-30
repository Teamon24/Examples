package benchmark.collection.strategies;

import benchmark.collection.utils.CollectionSuppliers;
import benchmark.collection.utils.ListMethods;
import benchmark.collection.strategy.MethodStrategy;
import benchmark.collection.strategy.SetStrategy;

import java.util.Collection;
import java.util.function.Function;
import java.util.function.Supplier;

import static benchmark.collection.utils.IndexSuppliers.firstIndex;
import static benchmark.collection.utils.IndexSuppliers.lastIndex;
import static benchmark.collection.utils.IndexSuppliers.middleIndex;

public class SetStrategies {

    public static <E> MethodStrategy setFirstStrategy(
        final Collection<E> collection,
        final Function<Collection<E>, Supplier<Collection<E>>> collectionSupplierGetter,
        final Supplier<E> elementSupplier)
    {
        Supplier<Collection<E>> collectionSupplier = CollectionSuppliers.sameCollection(collection);

        return new SetStrategy<>(
            collection.getClass(),
            collectionSupplier,
            ListMethods.setter(),
            firstIndex(),
            elementSupplier
        );
    }

    public static <E> MethodStrategy setMiddleStrategy(
        final Collection<E> collection,
        final Function<Collection<E>, Supplier<Collection<E>>> collectionSupplierGetter,
        final Supplier<E> elementSupplier)
    {
        Supplier<Collection<E>> collectionSupplier = CollectionSuppliers.sameCollection(collection);

        return new SetStrategy<>(
            collection.getClass(),
            collectionSupplier,
            ListMethods.setter(),
            middleIndex(),
            elementSupplier
        );
    }

    public static <E> MethodStrategy setLastStrategy(
        final Collection<E> collection,
        final Function<Collection<E>, Supplier<Collection<E>>> collectionSupplierGetter,
        final Supplier<E> elementSupplier)
    {
        Supplier<Collection<E>> collectionSupplier = CollectionSuppliers.sameCollection(collection);

        return new SetStrategy<>(
            collection.getClass(),
            collectionSupplier,
            ListMethods.setter(),
            lastIndex(),
            elementSupplier
        );
    }
}
