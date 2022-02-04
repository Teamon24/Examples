package core.collection.benchmark.core;

public class SetTestBuilder<E> extends CollectionTestBuilder<E> {

    @Override
    public CollectionTest<E> build() {
        return new SetTest<>(
            super.testsAmount,
            super.collection,
            super.collectionSupplier,
            super.newElementSupplier,
            super.existedElementSupplier
        );
    }

}
