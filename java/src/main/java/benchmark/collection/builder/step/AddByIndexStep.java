package benchmark.collection.builder.step;

import benchmark.collection.builder.AddStrategyBuilder;
import benchmark.collection.utils.TriConsumer;

import java.util.Collection;

public class AddByIndexStep<E> {

    private final AddStrategyBuilder<E> addStrategyBuilder;
    private final TriConsumer<Collection<E>, Integer, E> addByIndex;
    private Integer index;
    private E element;

    public AddByIndexStep(final AddStrategyBuilder<E> addStrategyBuilder,
                          final TriConsumer<Collection<E>, Integer, E> addByIndex)
    {
        this.addStrategyBuilder = addStrategyBuilder;
        this.addByIndex = addByIndex;
    }

    public AddStrategyBuilder<E> index(Integer index, E element) {
        this.index = index;
        this.element = element;
        return this.addStrategyBuilder;
    }

    public TriConsumer<Collection<E>, Integer, E> getAddByIndex() {
        return addByIndex;
    }

    public Integer getIndex() {
        return index;
    }

    public E getElement() {
        return element;
    }
}
