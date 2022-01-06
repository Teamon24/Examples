package core.collection.benchmark;

import core.collection.benchmark.pojo.MethodResult;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static core.collection.benchmark.strategies.AddStrategies.addByIndexStrategy;
import static core.collection.benchmark.strategies.AddStrategies.addElementStrategy;
import static core.collection.benchmark.strategies.AddStrategies.addFirstStrategy;
import static core.collection.benchmark.strategies.AddStrategies.addLastStrategy;
import static core.collection.benchmark.strategies.AddStrategies.addMiddleStrategy;
import static core.collection.benchmark.strategies.GetStrategies.getByIndexStrategy;
import static core.collection.benchmark.strategies.GetStrategies.getFirstStrategy;
import static core.collection.benchmark.strategies.GetStrategies.getLastStrategy;
import static core.collection.benchmark.strategies.GetStrategies.getMiddleStrategy;
import static core.collection.benchmark.strategies.RemoveStrategies.removeByIndexStrategy;
import static core.collection.benchmark.strategies.RemoveStrategies.removeElementStrategy;
import static core.collection.benchmark.strategies.RemoveStrategies.removeFirstStrategy;
import static core.collection.benchmark.strategies.RemoveStrategies.removeLastStrategy;
import static core.collection.benchmark.strategies.RemoveStrategies.removeMiddleStrategy;
import static core.collection.benchmark.strategies.SetStrategies.setByIndexStrategy;
import static core.collection.benchmark.strategies.SetStrategies.setFirstStrategy;
import static core.collection.benchmark.strategies.SetStrategies.setLastStrategy;
import static core.collection.benchmark.strategies.SetStrategies.setMiddleStrategy;
import static core.collection.benchmark.utils.CollectionSuppliers.newCollection;

public record CollectionTest<E>(int testsAmount, Collection<E> collection) {

    public List<MethodResult> test(
        final Supplier<E> newElementSupplier,
        final Supplier<E> existedElementSupplier,
        boolean enableLog,
        final int logStep
    ) {

        List<MethodTest> methodTests = this.getMethodTests(newElementSupplier, existedElementSupplier);
        return executeTests(methodTests, enableLog, logStep);
    }

    public List<MethodResult> testRandomIndex(
        final Supplier<E> elementSupplier,
        int logStep
    ) {
        Supplier<Collection<E>> createNew = newCollection(collection);
        Random random = new Random();

        Function<Collection<E>, Integer> randomIndex = (collection) -> random.nextInt(collection.size());

        List<MethodTest> methodTests = List.of(
            new MethodTest(testsAmount, removeByIndexStrategy(collection, createNew, randomIndex)),
            new MethodTest(testsAmount, addByIndexStrategy(collection, createNew, randomIndex, elementSupplier)),
            new MethodTest(testsAmount, setByIndexStrategy(collection, createNew, randomIndex, elementSupplier)),
            new MethodTest(testsAmount, getByIndexStrategy(collection, randomIndex))
        );

        return executeTests(methodTests, true, logStep);
    }

    private List<MethodResult> executeTests(final List<MethodTest> methodTests,
                                            final boolean enableLog,
                                            final Integer logStep) {
        return methodTests.stream()
            .map(it -> it.test(enableLog, logStep))
            .flatMap(Collection::stream)
            .collect(Collectors.toList());
    }

    private List<MethodTest> getMethodTests(
        final Supplier<E> newElementSupplier,
        final Supplier<E> existedElementSupplier) {
        if (collection instanceof List) {
            return List.of(
                new MethodTest(testsAmount, removeFirstStrategy(collection)),
                new MethodTest(testsAmount, removeMiddleStrategy(collection)),
                new MethodTest(testsAmount, removeLastStrategy(collection)),

                new MethodTest(testsAmount, addFirstStrategy(collection, newElementSupplier)),
                new MethodTest(testsAmount, addMiddleStrategy(collection, newElementSupplier)),
                new MethodTest(testsAmount, addLastStrategy(collection, newElementSupplier)),

                new MethodTest(testsAmount, getFirstStrategy(collection)),
                new MethodTest(testsAmount, getMiddleStrategy(collection)),
                new MethodTest(testsAmount, getLastStrategy(collection)),

                new MethodTest(testsAmount, setFirstStrategy(collection, newElementSupplier)),
                new MethodTest(testsAmount, setMiddleStrategy(collection, newElementSupplier)),
                new MethodTest(testsAmount, setLastStrategy(collection, newElementSupplier)),

                new MethodTest(testsAmount, addElementStrategy(collection, newElementSupplier)),
                new MethodTest(testsAmount, addElementStrategy(collection, newElementSupplier)),
                new MethodTest(testsAmount, addElementStrategy(collection, newElementSupplier)),

                new MethodTest(testsAmount, removeElementStrategy(collection, existedElementSupplier)),
                new MethodTest(testsAmount, removeElementStrategy(collection, existedElementSupplier)),
                new MethodTest(testsAmount, removeElementStrategy(collection, existedElementSupplier))
            );
        }

        if (collection instanceof Set) {
            return List.of(
                new MethodTest(testsAmount, removeElementStrategy(collection, existedElementSupplier)),
                new MethodTest(testsAmount, addElementStrategy(collection, newElementSupplier)
                ));
        }

        throw new RuntimeException("In methodTests getting no case for class: " + collection.getClass());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (CollectionTest) obj;
        return this.testsAmount == that.testsAmount &&
            Objects.equals(this.collection, that.collection);
    }

    @Override
    public int hashCode() {
        return Objects.hash(testsAmount, collection);
    }

    @Override
    public String toString() {
        return "CollectionTest[" +
            "testsAmount=" + testsAmount + ", " +
            "collection=" + collection + ']';
    }

}
