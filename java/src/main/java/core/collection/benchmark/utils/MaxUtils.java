package core.collection.benchmark.utils;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public final class MaxUtils {

    public static final String EMPTY = "";

    public static <Element> String longest(final Collection<Element> collectionTypes) {
        return longest(collectionTypes, it -> it);
    }

    public static <Element, Field> String longest(final Collection<Element> collection, Function<? super Element, ? extends Field> mapping) {
        return maxBy(collection, mapping);
    }

    public static <Element, Field> String maxBy(final Collection<Element> collection,
                                        Function<? super Element, ? extends Field> mapping
    ) {
        if (collection.isEmpty()) return EMPTY;
        List<? extends Field> nonNulls = collection
            .stream()
            .map(mapping)
            .filter(Objects::nonNull)
            .collect(Collectors.toList());

        if (nonNulls.isEmpty()) return "";

        return nonNulls.stream()
            .map(Object::toString)
            .max(Comparator.comparing(String::length))
            .get();
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }

    public static <E> Integer getMaxDigitsQuantityAfterDot(final List<E> averagedMethodResults,
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
