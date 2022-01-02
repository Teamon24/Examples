package core.collection.benchmark.builder.step;

import core.collection.benchmark.builder.AddStrategyBuilder;

import java.util.Collection;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class AddElementStep<E> {

    private AddStrategyBuilder<E> addStrategyBuilder;
    private BiConsumer<Collection<E>, E> addElement;
    private Supplier<E> elementSupplier;

    public AddElementStep(final AddStrategyBuilder<E> addStrategyBuilder,
                          final BiConsumer<Collection<E>, E> addElement)
    {
        this.addStrategyBuilder = addStrategyBuilder;
        this.addElement = addElement;
    }


    public AddStrategyBuilder<E> element(final Supplier<E> elementSupplier) {
        this.elementSupplier = elementSupplier;
        return this.addStrategyBuilder;
    }

    public BiConsumer<Collection<E>, E> getAddElement() {
        return addElement;
    }

    public Supplier<E> getElementSupplier() {
        return elementSupplier;
    }
}
