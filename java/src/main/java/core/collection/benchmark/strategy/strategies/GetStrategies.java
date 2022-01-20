package core.collection.benchmark.strategy.strategies;

import core.collection.benchmark.strategy.GetStrategy;
import core.collection.benchmark.strategy.abstrct.MethodStrategy;
import core.collection.benchmark.utils.ListMethods;
import core.collection.benchmark.utils.IndexSuppliers;

import java.util.Collection;
import java.util.function.Function;
import java.util.function.Supplier;

import static core.collection.benchmark.utils.CollectionSuppliers.*;

public final class GetStrategies {

    public static <E> MethodStrategy<E> getFirstStrategy(final Collection<E> collection) {
        return new GetStrategy<>(
            collection.getClass(),
            sameCollection(collection),
            ListMethods.getByIndex(),
            IndexSuppliers.firstIndex()
        );
    }

    public static <E> MethodStrategy<E> getMiddleStrategy(final Collection<E> collection) {
        return new GetStrategy<>(
            collection.getClass(),
            sameCollection(collection),
            ListMethods.getByIndex(),
            IndexSuppliers.middleIndex()
        );
    }

    public static <E> MethodStrategy<E> getLastStrategy(final Collection<E> collection) {
        return new GetStrategy<>(
            collection.getClass(),
            sameCollection(collection),
            ListMethods.getByIndex(),
            IndexSuppliers.lastIndex()
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
            IndexSuppliers.firstIndex()
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
            IndexSuppliers.middleIndex()
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
            IndexSuppliers.lastIndex()
        );
    }

    public static <E> MethodStrategy<E> getByIndexStrategy(
        final Collection<E> collection,
        final Function<Collection<E>, Integer> indexGetter
    ) {
        return new GetStrategy<>(
            collection.getClass(),
            sameCollection(collection),
            ListMethods.getByIndex(),
            indexGetter
        );
    }
}
