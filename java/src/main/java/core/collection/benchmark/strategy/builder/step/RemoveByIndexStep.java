package core.collection.benchmark.strategy.builder.step;

import core.collection.benchmark.strategy.builder.RemoveStrategyBuilder;


import java.util.Collection;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class RemoveByIndexStep<E> {

    private RemoveStrategyBuilder<E> removeStrategyBuilder;
    private BiConsumer<Collection<E>, Integer> removeByIndex;
    private Function<Collection<E>, Integer> indexSupplier;

    public RemoveByIndexStep(final RemoveStrategyBuilder<E> removeStrategyBuilder,
                             final BiConsumer<Collection<E>, Integer> removeByIndex) {
        this.removeStrategyBuilder = removeStrategyBuilder;
        this.removeByIndex = removeByIndex;
    }

    public RemoveStrategyBuilder<E> index(Function<Collection<E>, Integer> indexSupplier) {
        this.indexSupplier = indexSupplier;
        return removeStrategyBuilder;
    }

    public BiConsumer<Collection<E>, Integer> getRemoveByIndex() {
        return removeByIndex;
    }

    public Function<Collection<E>, Integer> getIndexSupplier() {
        return indexSupplier;
    }
}
