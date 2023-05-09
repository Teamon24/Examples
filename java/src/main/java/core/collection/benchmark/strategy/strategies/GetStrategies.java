package core.collection.benchmark.strategy.strategies;

import core.collection.benchmark.strategy.GetStrategy;
import core.collection.benchmark.utils.CollectionSuppliers;
import core.collection.benchmark.utils.IndexSuppliers;
import core.collection.benchmark.utils.ListMethods;
import core.collection.benchmark.strategy.abstrct.MethodStrategy;

import java.util.Collection;
import java.util.function.Function;
import java.util.function.Supplier;

public final class GetStrategies {

    public static <E> MethodStrategy<E> getFirstStrategy(final Collection<E> collection) {
        return new GetStrategy<>(
            collection.getClass(),
            CollectionSuppliers.sameCollection(collection),
            ListMethods.getByIndex(),
            IndexSuppliers.supplyFirst()
        );
    }

    public static <E> MethodStrategy<E> getMiddleStrategy(final Collection<E> collection) {
        return new GetStrategy<>(
            collection.getClass(),
            CollectionSuppliers.sameCollection(collection),
            ListMethods.getByIndex(),
            IndexSuppliers.supplyMiddle()
        );
    }

    public static <E> MethodStrategy<E> getLastStrategy(final Collection<E> collection) {
        return new GetStrategy<>(
            collection.getClass(),
            CollectionSuppliers.sameCollection(collection),
            ListMethods.getByIndex(),
            IndexSuppliers.supplyLast()
        );
    }

    public static <E> MethodStrategy<E> getFirstStrategy(
        final Collection<E> collection,
        final Supplier<Collection<E>> collectionSupplier
    ) {
        return new GetStrategy<>(
            collection.getClass(),
            collectionSupplier,
            ListMethods.getByIndex(),
            IndexSuppliers.supplyFirst()
        );
    }

    public static <E> MethodStrategy<E> getMiddleStrategy(
        final Collection<E> collection,
        final Supplier<Collection<E>> collectionSupplier
    ) {
        return new GetStrategy<>(
            collection.getClass(),
            collectionSupplier,
            ListMethods.getByIndex(),
            IndexSuppliers.supplyMiddle()
        );
    }

    public static <E> MethodStrategy<E> getLastStrategy(
        final Collection<E> collection,
        final Supplier<Collection<E>> collectionSupplier
    ) {
        return new GetStrategy<>(
            collection.getClass(),
            collectionSupplier,
            ListMethods.getByIndex(),
            IndexSuppliers.supplyLast()
        );
    }

    public static <E> MethodStrategy<E> getByIndexStrategy(
        Class<? extends Collection> collectionClass,
        Supplier<Collection<E>> collectionSupplier,
        final Function<Collection<E>, Integer> indexGetter
    ) {

        return new GetStrategy<>(
            collectionClass,
            collectionSupplier,
            ListMethods.getByIndex(),
            indexGetter
        );
    }
}
