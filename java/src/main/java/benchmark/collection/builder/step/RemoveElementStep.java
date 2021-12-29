package benchmark.collection.builder.step;

import benchmark.collection.builder.RemoveStrategyBuilder;

import java.util.Collection;
import java.util.function.BiConsumer;

public class RemoveElementStep<E> {

    private RemoveStrategyBuilder<E> removeStrategyBuilder;
    private BiConsumer<Collection<E>, E> removeElement;
    private E element;

    public RemoveElementStep(final RemoveStrategyBuilder<E> removeStrategyBuilder,
                             final BiConsumer<Collection<E>, E> removeElement) {
        this.removeStrategyBuilder = removeStrategyBuilder;
        this.removeElement = removeElement;
    }


    public RemoveStrategyBuilder<E> element(final E element) {
        this.element = element;
        return this.removeStrategyBuilder;
    }

    public BiConsumer<Collection<E>, E> getRemoveElement() {
        return removeElement;
    }

    public E getElement() {
        return element;
    }
}
