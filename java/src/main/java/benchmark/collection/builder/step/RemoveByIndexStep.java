package benchmark.collection.builder.step;

import benchmark.collection.builder.RemoveStrategyBuilder;


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

    public RemoveStrategyBuilder<E> index(Function<Collection<E>, Integer> index) {
        this.indexSupplier = index;
        return removeStrategyBuilder;
    }

    public BiConsumer<Collection<E>, Integer> getRemoveByIndex() {
        return removeByIndex;
    }

    public Function<Collection<E>, Integer> getIndexSupplier() {
        return indexSupplier;
    }
}
