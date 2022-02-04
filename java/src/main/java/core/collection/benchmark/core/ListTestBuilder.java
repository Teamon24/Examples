package core.collection.benchmark.core;

import core.collection.benchmark.utils.ElementSupplier;

import java.util.Collection;
import java.util.function.Function;
import java.util.function.Supplier;

import static core.collection.benchmark.utils.CollectionSuppliers.newCollection;

public class ListTestBuilder<E> extends CollectionTestBuilder<E> {

    private Function<Collection<E>, Integer> indexSupplier;

    public ListTestBuilder<E> testsAmount(int testsAmount) {
        return (ListTestBuilder<E>) super.testsAmount(testsAmount);
    }

    public ListTestBuilder<E> collection(Collection<E> collection) {
        return (ListTestBuilder<E>) super.collection(collection);
    }

    public ListTestBuilder<E> collectionSupplier(Supplier<Collection<E>> collectionSupplier) {
        return (ListTestBuilder<E>) super.collectionSupplier(collectionSupplier);
    }

    public ListTestBuilder<E> newElementSupplier(Supplier<E> newElementSupplier) {
        return (ListTestBuilder<E>) super.newElementSupplier(newElementSupplier);
    }

    public ListTestBuilder<E> existedElementSupplier(Supplier<E> existedElementSupplier) {
        return (ListTestBuilder<E>) super.existedElementSupplier(existedElementSupplier);
    }

    public ListTestBuilder<E> indexSupplier(Function<Collection<E>, Integer> indexSupplier) {
        this.indexSupplier = indexSupplier;
        return this;
    }

    public CollectionTest<E> build() {
        return new ListTest<>(
            super.testsAmount,
            super.collection,

            super.collectionSupplier == null ?
                newCollection(super.collection) :
                super.collectionSupplier,

            super.newElementSupplier == null ?
                ElementSupplier.getElementSequentiallyFrom(super.collection) :
                super.newElementSupplier,

            super.existedElementSupplier == null ?
                ElementSupplier.getElementSequentiallyFrom(super.collection) :
                super.existedElementSupplier,

            this.indexSupplier
        );
    }
}
