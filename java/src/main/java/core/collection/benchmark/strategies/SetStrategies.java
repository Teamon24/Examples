package core.collection.benchmark.strategies;

import core.collection.benchmark.utils.CollectionSuppliers;
import core.collection.benchmark.utils.ListMethods;
import core.collection.benchmark.strategy.MethodStrategy;
import core.collection.benchmark.strategy.SetStrategy;
import core.collection.benchmark.utils.IndexSuppliers;

import java.util.Collection;
import java.util.function.Function;
import java.util.function.Supplier;

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
            IndexSuppliers.firstIndex(),
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
            IndexSuppliers.middleIndex(),
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
            IndexSuppliers.lastIndex(),
            elementSupplier
        );
    }
}
