package core.collection.benchmark.strategy.strategies;

import core.collection.benchmark.strategy.abstrct.MethodStrategy;
import core.collection.benchmark.strategy.builder.RemoveStrategyBuilder;
import core.collection.benchmark.utils.ListMethods;

import java.util.Collection;
import java.util.function.Function;
import java.util.function.Supplier;

public final class RemoveStrategies {

    public static <E> MethodStrategy<E> removeByIndexStrategy(
        final Class<? extends Collection> collectionClass,
        final Supplier<Collection<E>> collectionSupplier,
        final Function<Collection<E>, Integer> indexGetter
    ) {
        return new RemoveStrategyBuilder<>(collectionSupplier)
            .collectionClass(collectionClass)
            .removeByIndex(ListMethods.removeByIndex())
            .index(indexGetter)
            .build();
    }

    public static <E> MethodStrategy<E> removeElementStrategy(
        final Class<? extends Collection> collectionClass,
        final Supplier<Collection<E>> collectionSupplier,
        final Supplier<E> removableElementSupplier
    ) {
        return new RemoveStrategyBuilder<>(collectionSupplier)
            .collectionClass(collectionClass)
            .removeElement(Collection::remove)
            .element(removableElementSupplier)
            .build();
    }
}
