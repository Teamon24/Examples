package core.collection.benchmark.strategy.builder.step;

import core.collection.benchmark.strategy.builder.RemoveStrategyBuilder;

import java.util.Collection;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class RemoveElementStep<E> {

    private RemoveStrategyBuilder<E> removeStrategyBuilder;
    private BiConsumer<Collection<E>, E> removeElement;
    private Supplier<E> elementSupplier;

    public RemoveElementStep(final RemoveStrategyBuilder<E> removeStrategyBuilder,
                             final BiConsumer<Collection<E>, E> removeElement) {
        this.removeStrategyBuilder = removeStrategyBuilder;
        this.removeElement = removeElement;
    }


    public RemoveStrategyBuilder<E> element(final Supplier<E> element) {
        this.elementSupplier = element;
        return this.removeStrategyBuilder;
    }

    public BiConsumer<Collection<E>, E> getRemoveElement() {
        return removeElement;
    }

    public Supplier<E> getElementSupplier() {
        return elementSupplier;
    }
}
