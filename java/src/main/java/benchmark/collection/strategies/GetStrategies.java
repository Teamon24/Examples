package benchmark.collection.strategies;

import benchmark.collection.strategy.GetStrategy;
import benchmark.collection.strategy.MethodStrategy;
import benchmark.collection.utils.ListMethods;

import java.util.Collection;
import java.util.function.Function;
import java.util.function.Supplier;

import static benchmark.collection.utils.IndexSuppliers.firstIndex;
import static benchmark.collection.utils.IndexSuppliers.lastIndex;
import static benchmark.collection.utils.IndexSuppliers.middleIndex;

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
            firstIndex()
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
            middleIndex()
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
            lastIndex()
        );
    }
}
