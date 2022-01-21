package core.collection.benchmark;

import core.collection.benchmark.utils.ElementSupplier;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;

import static core.collection.benchmark.strategy.strategies.AddStrategies.addByIndexStrategy;
import static core.collection.benchmark.strategy.strategies.AddStrategies.addElementStrategy;
import static core.collection.benchmark.strategy.strategies.GetStrategies.getByIndexStrategy;
import static core.collection.benchmark.strategy.strategies.RemoveStrategies.removeByIndexStrategy;
import static core.collection.benchmark.strategy.strategies.RemoveStrategies.removeElementStrategy;
import static core.collection.benchmark.strategy.strategies.SetStrategies.setByIndexStrategy;
import static core.collection.benchmark.utils.CollectionSuppliers.newCollection;

@AllArgsConstructor
public final class CollectionTest<E> {

    int testsAmount;
    Collection<E> collection;
    Supplier<Collection<E>> collectionSupplier;
    Supplier<E> newElementSupplier;
    Supplier<E> existedElementSupplier;

    public List<MethodTest<E>> getMethodTests(Function<Collection<E>, Integer> indexSupplier) {

        List<MethodTest<E>> tests = new ArrayList<>();
        tests.add(new MethodTest<>(testsAmount, addElementStrategy(collection, collectionSupplier, newElementSupplier)));
        tests.add(new MethodTest<>(testsAmount, removeElementStrategy(collection, existedElementSupplier)));

        if (collection instanceof List) {
            List<MethodTest<E>> listTest = List.of(
                new MethodTest<>(testsAmount, removeByIndexStrategy(collection, indexSupplier)),
                new MethodTest<>(testsAmount, addByIndexStrategy(collection, collectionSupplier, indexSupplier, newElementSupplier)),
                new MethodTest<>(testsAmount, setByIndexStrategy(collection, indexSupplier, newElementSupplier)),
                new MethodTest<>(testsAmount, getByIndexStrategy(collection, indexSupplier))
            );
            tests.addAll(listTest);
            return tests;
        }

        if (collection instanceof Set) {
            return tests;
        }

        throw new RuntimeException("Method tests are incompatible with class: " + collection.getClass());
    }

    public List<MethodTest<E>> getListMethodTests(Function<Collection<E>, Integer> indexSupplier) {

        List<MethodTest<E>> tests = new ArrayList<>();

        if (collection instanceof List) {
            List<MethodTest<E>> listTest = List.of(
                new MethodTest<>(testsAmount, removeByIndexStrategy(collection, indexSupplier)),
                new MethodTest<>(testsAmount, addByIndexStrategy(collection, collectionSupplier, indexSupplier, newElementSupplier)),
                new MethodTest<>(testsAmount, setByIndexStrategy(collection, indexSupplier, newElementSupplier)),
                new MethodTest<>(testsAmount, getByIndexStrategy(collection, indexSupplier))
            );
            tests.addAll(listTest);
            return tests;
        }

        throw new RuntimeException("Method tests are incompatible with class: " + collection.getClass());
    }

    public static class CollectionTestBuilder<E> {
        private int testsAmount;
        private Collection<E> collection;
        private Supplier<Collection<E>> collectionSupplier;
        private Supplier<E> newElementSupplier;
        private Supplier<E> existedElementSupplier;

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

        public CollectionTest<E> build() {
            return new CollectionTest<>(
                this.testsAmount,
                this.collection,
                this.collectionSupplier == null ? newCollection(this.collection) : this.collectionSupplier,
                this.newElementSupplier == null ? ElementSupplier.getElementAndDiscard(this.collection) : this.newElementSupplier,
                this.existedElementSupplier == null ? ElementSupplier.getElementAndDiscard(this.collection) : this.existedElementSupplier);
        }
    }
}
