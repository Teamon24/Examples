package core.collection.benchmark.core;

import java.util.Collection;
import java.util.function.Supplier;

public class CollectionTestBuilder<E> {
    protected int testsAmount;
    protected Collection<E> collection;
    protected Supplier<Collection<E>> collectionSupplier;
    protected Supplier<E> newElementSupplier;
    protected Supplier<E> existedElementSupplier;
    protected CollectionTestBuilder<E> builder;

    public CollectionTestBuilder<E> testsAmount(int testsAmount) {
        this.testsAmount = testsAmount;
        return this;
    }

    public CollectionTestBuilder<E> collection(Collection<E> collection) {
        this.collection = collection;
        return this;
    }

    public CollectionTestBuilder<E> collectionSupplier(Supplier<Collection<E>> collectionSupplier) {
        this.collectionSupplier = collectionSupplier;
        return this;
    }

    public CollectionTestBuilder<E> newElementSupplier(Supplier<E> newElementSupplier) {
        this.newElementSupplier = newElementSupplier;
        return this;
    }

    public CollectionTestBuilder<E> existedElementSupplier(Supplier<E> existedElementSupplier) {
        this.existedElementSupplier = existedElementSupplier;
        return this;
    }

    public ListTestBuilder<E> lists() {
        this.builder = new ListTestBuilder<>();
        return (ListTestBuilder<E>) builder;
    }

    public SetTestBuilder<E> sets() {
        this.builder = new SetTestBuilder<>();
        return (SetTestBuilder<E>) builder;
    }

    public CollectionTest<E> build() {
        return this.builder.build();
    }
}
