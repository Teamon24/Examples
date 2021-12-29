package benchmark.collection.builder;

import benchmark.collection.builder.step.AddByIndexStep;
import benchmark.collection.builder.step.AddElementStep;
import benchmark.collection.strategy.AddByIndexStrategy;
import benchmark.collection.strategy.AddElementStrategy;
import benchmark.collection.strategy.MethodStrategy;
import benchmark.collection.utils.TriConsumer;

import java.util.Collection;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class AddStrategyBuilder<E> {

    private Class<? extends Collection> collectionClass;
    private Supplier<Collection<E>> collectionSupplier;
    private AddByIndexStep<E> addByIndexStep;
    private AddElementStep<E> addElementStep;

    public AddStrategyBuilder(Supplier<Collection<E>> collectionSupplier) {
        this.collectionSupplier = collectionSupplier;
    }

    public AddStrategyBuilder<E> collectionClass(final Class<? extends Collection> collectionClass) {
        this.collectionClass = collectionClass;
        return this;
    }

    public AddByIndexStep<E> addByIndex(final TriConsumer<Collection<E>, Integer, E> addByIndex) {
        this.addByIndexStep = new AddByIndexStep<>(this, addByIndex);
        return this.addByIndexStep;
    }

    public AddElementStep<E> addElement(BiConsumer<Collection<E>, E> addElement) {
        this.addElementStep = new AddElementStep<>(this, addElement);
        return this.addElementStep;
    }

    public MethodStrategy<E> build() {
        if (addByIndexStep != null) {
            return new AddByIndexStrategy<>(
                this.collectionClass,
                this.collectionSupplier,
                this.addByIndexStep.getAddByIndex(),
                this.addByIndexStep.getIndex(),
                this.addByIndexStep.getElement());
        }

        if (addElementStep != null) {
            return new AddElementStrategy<>(
                this.collectionClass,
                this.collectionSupplier,
                this.addElementStep.getAddElement(),
                this.addElementStep.getElement());
        }

        throw new RuntimeException("Index and element should not be null both");
    }

}
