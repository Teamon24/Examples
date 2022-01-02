package core.collection.benchmark;

import core.collection.benchmark.pojo.MethodResult;
import core.collection.benchmark.utils.CollectionSuppliers;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static core.collection.benchmark.strategies.AddStrategies.addFirstStrategy;
import static core.collection.benchmark.strategies.AddStrategies.addLastStrategy;
import static core.collection.benchmark.strategies.AddStrategies.addMiddleStrategy;
import static core.collection.benchmark.strategies.AddStrategies.addElementStrategy;
import static core.collection.benchmark.strategies.GetStrategies.getFirstStrategy;
import static core.collection.benchmark.strategies.GetStrategies.getLastStrategy;
import static core.collection.benchmark.strategies.GetStrategies.getMiddleStrategy;
import static core.collection.benchmark.strategies.RemoveStrategies.removeFirstStrategy;
import static core.collection.benchmark.strategies.RemoveStrategies.removeLastStrategy;
import static core.collection.benchmark.strategies.RemoveStrategies.removeMiddleStrategy;
import static core.collection.benchmark.strategies.RemoveStrategies.removeElementStrategy;
import static core.collection.benchmark.strategies.SetStrategies.setFirstStrategy;
import static core.collection.benchmark.strategies.SetStrategies.setLastStrategy;
import static core.collection.benchmark.strategies.SetStrategies.setMiddleStrategy;

public class CollectionTest<E> {
    public final Function<Collection<E>, Supplier<Collection<E>>> SAME_COLLECTION = CollectionSuppliers::sameCollection;
    public final Function<Collection<E>, Supplier<Collection<E>>> NEW_COLLECTION = CollectionSuppliers::newCollection;
    private final int testsAmount;
    private final Collection<E> collection;

    public CollectionTest(final int testsAmount, final Collection<E> collection) {
        this.testsAmount = testsAmount;
        this.collection = collection;
    }

    public List<MethodResult> testList(
        final Supplier<E> addableElementSupplier,
        final Supplier<E> removableElementSupplier, int logStep
    )
    {
        List<MethodTest> methodTests = List.of(
            new MethodTest(testsAmount, removeFirstStrategy(collection, NEW_COLLECTION)),
            new MethodTest(testsAmount, removeMiddleStrategy(collection, NEW_COLLECTION)),
            new MethodTest(testsAmount, removeLastStrategy(collection, NEW_COLLECTION)),

            new MethodTest(testsAmount, addFirstStrategy(collection, NEW_COLLECTION, addableElementSupplier)),
            new MethodTest(testsAmount, addMiddleStrategy(collection, NEW_COLLECTION, addableElementSupplier)),
            new MethodTest(testsAmount, addLastStrategy(collection, NEW_COLLECTION, addableElementSupplier)),

            new MethodTest(testsAmount, getFirstStrategy(collection, SAME_COLLECTION)),
            new MethodTest(testsAmount, getMiddleStrategy(collection, SAME_COLLECTION)),
            new MethodTest(testsAmount, getLastStrategy(collection, SAME_COLLECTION)),

            new MethodTest(testsAmount, setFirstStrategy(collection, SAME_COLLECTION, addableElementSupplier)),
            new MethodTest(testsAmount, setMiddleStrategy(collection, SAME_COLLECTION, addableElementSupplier)),
            new MethodTest(testsAmount, setLastStrategy(collection, SAME_COLLECTION, addableElementSupplier)),

            new MethodTest(testsAmount, addElementStrategy(collection, SAME_COLLECTION, addableElementSupplier)),
            new MethodTest(testsAmount, addElementStrategy(collection, SAME_COLLECTION, addableElementSupplier)),
            new MethodTest(testsAmount, addElementStrategy(collection, SAME_COLLECTION, addableElementSupplier)),

            new MethodTest(testsAmount, removeElementStrategy(collection, SAME_COLLECTION, removableElementSupplier)),
            new MethodTest(testsAmount, removeElementStrategy(collection, SAME_COLLECTION, removableElementSupplier)),
            new MethodTest(testsAmount, removeElementStrategy(collection, SAME_COLLECTION, removableElementSupplier))
        );

        return test(methodTests, logStep);
    }

    public List<MethodResult> testSet(
        final Supplier<E> newElementSupplier,
        final Supplier<E> existedElementSupplier, int logStep
    ) {
        List<MethodTest> methodTests = List.of(
            new MethodTest(testsAmount, removeElementStrategy(collection, SAME_COLLECTION, existedElementSupplier)),
            new MethodTest(testsAmount, addElementStrategy(collection, SAME_COLLECTION, newElementSupplier)
        ));

        return test(methodTests, logStep);
    }

    private List<MethodResult> test(final List<MethodTest> methodTests, int logStep) {
        List<MethodResult> collect = methodTests.stream()
            .map(it -> it.test(true, logStep))
            .flatMap(Collection::stream)
            .collect(Collectors.toList());

        return collect;
    }
}
