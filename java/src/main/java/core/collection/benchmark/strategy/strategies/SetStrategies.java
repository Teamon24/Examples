package core.collection.benchmark.strategy.strategies;

import core.collection.benchmark.utils.CollectionSuppliers;
import core.collection.benchmark.utils.IndexSuppliers;
import core.collection.benchmark.utils.ListMethods;
import core.collection.benchmark.strategy.abstrct.MethodStrategy;
import core.collection.benchmark.strategy.SetStrategy;

import java.util.Collection;
import java.util.function.Function;
import java.util.function.Supplier;

public final class SetStrategies {

    public static <E> MethodStrategy<E> setFirstStrategy(final Collection<E> collection, final Supplier<E> elementSupplier) {
        return setFirstStrategy(collection, CollectionSuppliers.sameCollection(collection), elementSupplier);
    }

    public static <E> MethodStrategy<E> setMiddleStrategy(final Collection<E> collection, final Supplier<E> elementSupplier) {
        return setMiddleStrategy(collection, CollectionSuppliers.sameCollection(collection), elementSupplier);
    }

    public static <E> MethodStrategy<E> setLastStrategy(final Collection<E> collection, final Supplier<E> elementSupplier) {
        return setLastStrategy(collection, CollectionSuppliers.sameCollection(collection), elementSupplier);
    }

    public static <E> MethodStrategy<E> setFirstStrategy(
        final Collection<E> collection,
        final Supplier<Collection<E>> collectionSupplier,
        final Supplier<E> elementSupplier)
    {
        return new SetStrategy<>(
            collection.getClass(),
            collectionSupplier,
            ListMethods.setter(),
            IndexSuppliers.supplyFirst(),
            elementSupplier
        );
    }

    public static <E> MethodStrategy<E> setMiddleStrategy(
        final Collection<E> collection,
        final Supplier<Collection<E>> collectionSupplier,
        final Supplier<E> elementSupplier)
    {
        return new SetStrategy<>(
            collection.getClass(),
            collectionSupplier,
            ListMethods.setter(),
            IndexSuppliers.supplyMiddle(),
            elementSupplier
        );
    }

    public static <E> MethodStrategy<E> setLastStrategy(
        final Collection<E> collection,
        final Supplier<Collection<E>> collectionSupplier,
        final Supplier<E> elementSupplier)
    {
        return new SetStrategy<>(
            collection.getClass(),
            collectionSupplier,
            ListMethods.setter(),
            IndexSuppliers.supplyLast(),
            elementSupplier
        );
    }

    public static <E> MethodStrategy<E> setByIndexStrategy(
        Class<? extends Collection> collectionClass,
        Supplier<Collection<E>> collectionSupplier,
        final Function<Collection<E>, Integer> indexGetter,
        final Supplier<E> elementSupplier
    )
    {
        return new SetStrategy<>(
            collectionClass,
            collectionSupplier,
            ListMethods.setter(),
            indexGetter,
            elementSupplier
        );
    }
}