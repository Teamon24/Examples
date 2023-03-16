package core.collection.benchmark.strategy.abstrct;

import core.collection.benchmark.pojo.MethodResult;
import core.collection.benchmark.pojo.MethodType;
import core.collection.benchmark.utils.PrintResultBuilder;
import utils.PrintUtils;

import java.util.Collection;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class IndexAndElementStrategy<E> extends MethodStrategy<E> {

    protected final Function<Collection<E>, Integer> indexGetter;
    protected Integer index;

    protected final Supplier<E> elementGetter;
    protected E element;
    
    public IndexAndElementStrategy(Class<?> collectionClass,
                                   Supplier<Collection<E>> collectionSupplier,
                                   Function<Collection<E>, Integer> indexGetter,
                                   Supplier<E> elementGetter) 
    {
        super(collectionClass, collectionSupplier);
        this.indexGetter = indexGetter;
        this.elementGetter = elementGetter;
    }

    public long execute(Collection<E> collection) {
        this.index = this.indexGetter.apply(collection);
        this.element = this.elementGetter.get();
        return super.timer.count((ignored) -> method(collection, this.index, this.element));
    }

    public MethodResult<E> createResult(long executionTime) {
        return new MethodResult<>(getCollectionType(), getMethodType(), this.index, this.element, executionTime);
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
            .index(this.index)
            .executionTime(executionTime).build();
        System.out.println(build);
    }

    public abstract void method(Collection<E> collection, Integer index, E element);

    @Override
    public MethodType getMethodType() {
        return null;
    }
}
