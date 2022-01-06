package core.collection.benchmark.builder.step;

import core.collection.benchmark.builder.AddStrategyBuilder;
import core.collection.benchmark.utils.TriConsumer;

import java.util.Collection;
import java.util.function.Function;
import java.util.function.Supplier;

public class AddByIndexStep<E> {

    private final AddStrategyBuilder<E> addStrategyBuilder;
    private final TriConsumer<Collection<E>, Integer, E> addByIndex;
    private Function<Collection<E>, Integer> indexSupplier;
    private Supplier<E> elementSupplier;

    public AddByIndexStep(final AddStrategyBuilder<E> addStrategyBuilder,
                          final TriConsumer<Collection<E>, Integer, E> addByIndex)
    {
        this.addStrategyBuilder = addStrategyBuilder;
        this.addByIndex = addByIndex;
    }

    public AddByIndexStep<E> index(Function<Collection<E>, Integer> indexSupplier) {
        this.indexSupplier = indexSupplier;
        return this;
    }

    public AddStrategyBuilder<E> element(Supplier<E> elementSupplier) {
        this.elementSupplier = elementSupplier;
        return this.addStrategyBuilder;
    }

    public TriConsumer<Collection<E>, Integer, E> getAddByIndex() {
        return addByIndex;
    }

    public Function<Collection<E>, Integer> getIndexSupplier() {
        return indexSupplier;
    }

    public Supplier<E> getElementSupplier() {
        return elementSupplier;
    }
}
