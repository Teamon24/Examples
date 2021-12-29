package benchmark.collection.utils;

import benchmark.collection.pojo.AveragedMethodResult;
import benchmark.collection.pojo.Histogram;
import benchmark.collection.pojo.MethodType;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

public final class MaxUtils {
    static String getLongestMethodName(final Set<MethodType> methodTypes) {
        return methodTypes.stream().max(Comparator.comparingInt(it -> it.getValue().length())).get().getValue();
    }

    static String getLongestHistogramColumn(final List<Histogram> histograms) {
        return histograms.stream().max(Comparator.comparingInt(it -> it.getHistogramColumn().length())).get().getHistogramColumn();
    }

    static String getLongestCollectionName(final Set<String> collectionTypes) {
        return collectionTypes.stream().max(Comparator.comparing(String::length)).get();
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
