package core.collection.benchmark.strategy;

import core.collection.benchmark.pojo.MethodResult;
import core.collection.benchmark.pojo.MethodType;
import core.collection.benchmark.utils.TimeMeasureStrategy;
import core.collection.comparation.Timer;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Collection;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class MethodStrategy<E> {

    private final Class<?> collectionClass;
    private final Supplier<Collection<E>> collectionSupplier;
    private final Function<Collection<E>, Integer> indexGetter;
    private final Supplier<E> elementGetter;

    private Timer timer;
    private Integer index;
    private E element;

    public MethodStrategy(final Class<?> collectionClass,
                          final Supplier<Collection<E>> collectionSupplier,
                          final Function<Collection<E>, Integer> indexGetter,
                          final Supplier<E> elementGetter)
    {
        this.collectionClass = collectionClass;
        this.collectionSupplier = collectionSupplier;
        this.indexGetter = indexGetter;
        this.elementGetter = elementGetter;
    }

    public long executeAndGetTime(TimeMeasureStrategy timeMeasureStrategy) {
        createTimerIfNull(timeMeasureStrategy);
        Collection<E> collection = this.collectionSupplier.get();
        if (this.indexGetter != null && this.elementGetter != null) {
            this.index = this.indexGetter.apply(collection);
            this.element = this.elementGetter.get();
            return this.timer.count((ignored) -> method(collection, this.index, this.element));
        }

        if (this.indexGetter != null) {
            this.index = this.indexGetter.apply(collection);
            return this.timer.count((ignored) -> method(collection, this.index));
        }

        if (this.elementGetter == null) {
            String template = "There should not be case: indexGetter and elementGetter are absent";
            throw new RuntimeException(template);
        }

        this.element = this.elementGetter.get();
        return this.timer.count((ignored) -> method(collection, this.element));
    }

    public String getCollectionType() {
        return this.collectionClass.getSimpleName();
    }

    public abstract void method(Collection<E> collection, Integer index, E element);
    public abstract void method(Collection<E> collection, Integer index);
    public abstract void method(Collection<E> collection, E element);

    public MethodResult createResult(long executionTime) {
        return new MethodResult(getCollectionType(), getMethodType(), this.index, this.element, executionTime);
    }
    public abstract MethodType getMethodType();

    private void createTimerIfNull(final TimeMeasureStrategy timeMeasureStrategy) {
        if (timer == null) {
            timer = new Timer(timeMeasureStrategy);
        }
    }

    public static void throwNoImplementationShouldBe(Pair<String, String>[] pairs) {
        StringBuilder stringBuilder = new StringBuilder();
        String pairTemplate = "%s is %s";
        String firstPairString = getString(pairTemplate, pairs[0]);
        stringBuilder.append(firstPairString);

        for (int i = 1; i < pairs.length; i++) {
            String message = getString(pairTemplate, pairs[i]);
            stringBuilder.append(" and ").append(message);
        }

        String template = "There should not be implementation: %s";
        throw new RuntimeException(String.format(template, stringBuilder));
    }

    private static String getString(final String template, final Pair<String, String> pairs) {
        String format = String.format(template, pairs.getKey(), pairs.getValue());
        return format;
    }

}
