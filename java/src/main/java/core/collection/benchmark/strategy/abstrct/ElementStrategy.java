package core.collection.benchmark.strategy.abstrct;

import core.collection.benchmark.pojo.MethodResult;
import core.collection.benchmark.pojo.MethodType;
import core.collection.benchmark.utils.PrintResultBuilder;

import java.util.Collection;
import java.util.function.Supplier;

public abstract class ElementStrategy<E> extends MethodStrategy<E> {
    protected final Supplier<E> elementSupplier;
    protected E element;

    public ElementStrategy(
        Class<?> collectionClass,
        Supplier<Collection<E>> collectionSupplier,
        Supplier<E> elementSupplier
    ) {
        super(collectionClass, collectionSupplier);
        this.elementSupplier = elementSupplier;
    }

    public long execute(Collection<E> collection) {
        this.element = this.elementSupplier.get();
        return super.timer.count((ignored) -> method(collection, this.element));
    }

    public MethodResult<E> createResult(long executionTime) {
        return new MethodResult<>(getCollectionType(), getMethodType(), null, this.element, executionTime);
    }

    @Override
    public void printTestResult(
        int testsAmount,
        int testNumber,
        String collectionType,
        MethodType methodType,
        long executionTime
    ) {
        String build = new PrintResultBuilder<E>()
            .testAmount(testsAmount)
            .testNumber(testNumber)
            .collection(collectionType)
            .method(methodType.getValue())
            .element(this.element)
            .executionTime(executionTime).build();
        System.out.println(build);
    }

    public abstract void method(Collection<E> collection, E element);
}
