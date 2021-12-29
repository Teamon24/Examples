package benchmark.collection.builder.step;

import benchmark.collection.builder.AddStrategyBuilder;

import java.util.Collection;
import java.util.function.BiConsumer;

public class AddElementStep<E> {

    private AddStrategyBuilder<E> addStrategyBuilder;
    private BiConsumer<Collection<E>, E> addElement;
    private E element;

    public AddElementStep(final AddStrategyBuilder<E> addStrategyBuilder,
                          final BiConsumer<Collection<E>, E> addElement)
    {
        this.addStrategyBuilder = addStrategyBuilder;
        this.addElement = addElement;
    }


    public AddStrategyBuilder<E> element(final E element) {
        this.element = element;
        return this.addStrategyBuilder;
    }

    public BiConsumer<Collection<E>, E> getAddElement() {
        return addElement;
    }

    public E getElement() {
        return element;
    }
}
