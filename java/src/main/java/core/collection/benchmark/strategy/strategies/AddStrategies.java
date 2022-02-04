package core.collection.benchmark.strategy.strategies;

import core.collection.benchmark.strategy.abstrct.MethodStrategy;
import core.collection.benchmark.strategy.builder.AddStrategyBuilder;
import core.collection.benchmark.utils.ListMethods;

import java.util.Collection;
import java.util.function.Function;
import java.util.function.Supplier;

public final class AddStrategies {

    public static <E> MethodStrategy<E> addByIndexStrategy(
        final Class<? extends Collection> collectionClass,
        final Supplier<Collection<E>> collectionSupplier,
        final Function<Collection<E>, Integer> indexSupplier,
        final Supplier<E> elementSupplier
    ) {
        return new AddStrategyBuilder<>(collectionSupplier)
            .collectionClass(collectionClass)
            .addByIndex(ListMethods.addByIndex())
            .index(indexSupplier)
            .element(elementSupplier)
            .build();
    }

    public static <E> MethodStrategy<E> addElementStrategy(
        final Class<? extends Collection> collectionClass,
        final Supplier<Collection<E>> collectionSupplier,
        final Supplier<E> elementSupplier
    ) {
        return new AddStrategyBuilder<>(collectionSupplier)
            .collectionClass(collectionClass)
            .addElement(Collection::add)
            .element(elementSupplier)
            .build();
    }
}