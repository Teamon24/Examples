package core.collection.benchmark.builder;

import core.collection.benchmark.builder.step.RemoveByIndexStep;
import core.collection.benchmark.builder.step.RemoveElementStep;
import core.collection.benchmark.strategy.abstrct.MethodStrategy;
import core.collection.benchmark.strategy.RemoveByIndexStrategy;
import core.collection.benchmark.strategy.RemoveElementStrategy;

import java.util.Collection;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class RemoveStrategyBuilder<E> {

    private Class<? extends Collection> collectionClass;
    private final Supplier<Collection<E>> collectionSupplier;
    private RemoveByIndexStep<E> removeByIndexStep;
    private RemoveElementStep<E> removeElementStep;

    public RemoveStrategyBuilder(Supplier<Collection<E>> collectionSupplier) {
        this.collectionSupplier = collectionSupplier;
    }

    public RemoveStrategyBuilder<E> collectionClass(final Class<? extends Collection> collectionClass) {
        this.collectionClass = collectionClass;
        return this;
    }

    public RemoveByIndexStep<E> removeByIndex(BiConsumer<Collection<E>, Integer> removeByIndex) {
        this.removeByIndexStep = new RemoveByIndexStep<>(this, removeByIndex);
        return this.removeByIndexStep;
    }

    public RemoveElementStep<E> removeElement(BiConsumer<Collection<E>, E> removeElement) {
        this.removeElementStep = new RemoveElementStep<>(this, removeElement);
        return this.removeElementStep;
    }

    public MethodStrategy<E> build() {
        if (removeByIndexStep != null) {
            return new RemoveByIndexStrategy<>(
                this.collectionClass,
                this.collectionSupplier,
                this.removeByIndexStep.getRemoveByIndex(),
                this.removeByIndexStep.getIndexSupplier());
        }

        if (removeElementStep != null) {
            return new RemoveElementStrategy<>(
                this.collectionClass,
                this.collectionSupplier,
                this.removeElementStep.getRemoveElement(),
                this.removeElementStep.getElementSupplier());
        }

        throw new RuntimeException("Remove-by-index logic and remove-element logic should not be null both");
    }
}