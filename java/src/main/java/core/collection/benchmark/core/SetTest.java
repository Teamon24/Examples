package core.collection.benchmark.core;

import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

import static core.collection.benchmark.strategy.strategies.AddStrategies.addElementStrategy;
import static core.collection.benchmark.strategy.strategies.RemoveStrategies.removeElementStrategy;

public class SetTest<E> extends CollectionTest<E> {

    public SetTest(
        int testsAmount,
        Collection<E> collection,
        Supplier<Collection<E>> collectionSupplier,
        Supplier<E> newElementSupplier,
        Supplier<E> existedElementSupplier
    ) {
        super(testsAmount, collection, collectionSupplier, newElementSupplier, existedElementSupplier);
    }

    @Override
    public List<MethodTest<E>> getMethodsTest() {
        Class<? extends Collection> collectionClass = collection.getClass();
        return List.of(
            new MethodTest<>(testsAmount, removeElementStrategy(collectionClass, collectionSupplier, existedElementSupplier)),
            new MethodTest<>(testsAmount, addElementStrategy(collectionClass, collectionSupplier, newElementSupplier))
        );
    }

}
