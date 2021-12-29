package benchmark.collection.builder.step;

import benchmark.collection.builder.RemoveStrategyBuilder;


import java.util.Collection;
import java.util.function.BiConsumer;

public class RemoveByIndexStep<E> {

    private RemoveStrategyBuilder<E> removeStrategyBuilder;
    private BiConsumer<Collection<E>, Integer> removeByIndex;
    private Integer index;

    public RemoveByIndexStep(final RemoveStrategyBuilder<E> removeStrategyBuilder,
                             final BiConsumer<Collection<E>, Integer> removeByIndex) {
        this.removeStrategyBuilder = removeStrategyBuilder;
        this.removeByIndex = removeByIndex;
    }

    public RemoveStrategyBuilder<E> index(Integer index) {
        this.index = index;
        return removeStrategyBuilder;
    }

    public BiConsumer<Collection<E>, Integer> getRemoveByIndex() {
        return removeByIndex;
    }

    public Integer getIndex() {
        return index;
    }
}
