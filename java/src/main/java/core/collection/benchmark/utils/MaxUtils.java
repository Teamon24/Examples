package core.collection.benchmark.utils;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

public final class MaxUtils {

    public static final String EMPTY = "";

    static <Element> String longest(final Collection<Element> collectionTypes) {
        return longestBy(collectionTypes, it -> it);
    }

    static <Element, Field> String longestBy(final Collection<Element> collection, Function<Element, Field> mapping) {
        return maxBy(collection, mapping);
    }

    static <Element, Field> String maxBy(final Collection<Element> collection,
                                        Function<Element, Field> mapping
    ) {
        if (collection.isEmpty()) return EMPTY;
        return collection
            .stream()
            .map(mapping)
            .map(Object::toString)
            .max(Comparator.comparing(String::length))
            .get();
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }

    public static <E> Integer getMaxDigitsAmountAfterDot(final List<E> averagedMethodResults,
                                                         final Function<E, Double> getField)
    {
        return averagedMethodResults.stream()
            .max(Comparator.comparing(getField))
            .map(getField)
            .map(MaxUtils::getDigitsAmountAfterDot).get();
    }
    private static int getDigitsAmountAfterDot(final Double averageTime) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

}
