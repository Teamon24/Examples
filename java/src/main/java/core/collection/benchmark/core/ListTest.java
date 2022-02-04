package core.collection.benchmark.core;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

import static core.collection.benchmark.strategy.strategies.AddStrategies.addByIndexStrategy;
import static core.collection.benchmark.strategy.strategies.GetStrategies.getByIndexStrategy;
import static core.collection.benchmark.strategy.strategies.RemoveStrategies.removeByIndexStrategy;
import static core.collection.benchmark.strategy.strategies.SetStrategies.setByIndexStrategy;

public class ListTest<E> extends CollectionTest<E> {

    private final Function<Collection<E>, Integer> indexSupplier;

    public ListTest(
        int testsAmount,
        Collection<E> collection,
        Supplier<Collection<E>> collectionSupplier,
        Supplier<E> newElementSupplier,
        Supplier<E> existedElementSupplier,
        Function<Collection<E>, Integer> indexSupplier
    ) {
        super(testsAmount, collection, collectionSupplier, newElementSupplier, existedElementSupplier);
        this.indexSupplier = indexSupplier;
    }

    @Override
    public List<MethodTest<E>> getMethodsTest() {
        return List.of(
            new MethodTest<>(testsAmount, removeByIndexStrategy(collection, indexSupplier)),
            new MethodTest<>(testsAmount, addByIndexStrategy(collection.getClass(), collectionSupplier, indexSupplier, newElementSupplier)),
            new MethodTest<>(testsAmount, setByIndexStrategy(collection, indexSupplier, newElementSupplier)),
            new MethodTest<>(testsAmount, getByIndexStrategy(collection, indexSupplier))
        );
    }
}
