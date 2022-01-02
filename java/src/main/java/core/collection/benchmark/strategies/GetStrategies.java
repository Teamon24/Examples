package core.collection.benchmark.strategies;

import core.collection.benchmark.strategy.GetStrategy;
import core.collection.benchmark.strategy.MethodStrategy;
import core.collection.benchmark.utils.ListMethods;
import core.collection.benchmark.utils.IndexSuppliers;

import java.util.Collection;
import java.util.function.Function;
import java.util.function.Supplier;

public class GetStrategies {
    public static <E> MethodStrategy<E> getFirstStrategy(
        final Collection<E> collection,
        final Function<Collection<E>, Supplier<Collection<E>>> collectionSupplierFunction)
    {
        Supplier<Collection<E>> collectionSupplier = collectionSupplierFunction.apply(collection);

        return new GetStrategy<>(
            collection.getClass(),
            collectionSupplier,
            ListMethods.getByIndex(),
            IndexSuppliers.firstIndex()
        );
    }

    public static <E> MethodStrategy<E> getMiddleStrategy(
        final Collection<E> collection,
        final Function<Collection<E>, Supplier<Collection<E>>> collectionSupplierFunction)
    {
        Supplier<Collection<E>> collectionSupplier = collectionSupplierFunction.apply(collection);

        return new GetStrategy<E>(
            collection.getClass(),
            collectionSupplier,
            ListMethods.getByIndex(),
            IndexSuppliers.middleIndex()
        );
    }

    public static <E> MethodStrategy<E> getLastStrategy(
        final Collection<E> collection,
        final Function<Collection<E>, Supplier<Collection<E>>> collectionSupplierFunction)
    {
        Supplier<Collection<E>> collectionSupplier = collectionSupplierFunction.apply(collection);

        return new GetStrategy<>(
            collection.getClass(),
            collectionSupplier,
            ListMethods.getByIndex(),
            IndexSuppliers.lastIndex()
        );
    }
}
