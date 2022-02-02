package core.collection.benchmark.utils;

import core.collection.benchmark.pojo.AveragedMethodResult;
import core.collection.benchmark.pojo.Histogram;
import core.collection.benchmark.pojo.MethodType;
import utils.IndentUtils;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static core.collection.benchmark.utils.StreamUtils.getUniques;
import static utils.PrintUtils.println;

public final class HistogramWithIndexUtils extends HistogramUtils {

    public static <E extends Comparable<E>> void printHistogram(final List<AveragedMethodResult<E>> results) {
        StringBuilder stringBuilder = getStringHistograms(results, Histogram::getIndex);
        println(stringBuilder);
    }

    public static <E extends Comparable<E>> void printHistogramNoIndex(final List<AveragedMethodResult<E>> results) {
        StringBuilder stringBuilder = HistogramWithIndexUtils.getStringHistograms(results, Histogram::getElement);
        println(stringBuilder);
    }

    public static <E extends Comparable<E>, G extends Comparable<? super G>> StringBuilder getStringHistograms(
        List<AveragedMethodResult<E>> results,
        Function<? super Histogram, ? extends G> fieldExtractor
    ) {
        if (results.isEmpty()) {
            return new StringBuilder().append("No results");
        }

        Set<String> collectionTypes = StreamUtils.getUniques(results, AveragedMethodResult::getCollectionClass);
        checkSize(collectionTypes);

        HashMap<String, String> collectionTypeAndMark = mapToMarks(collectionTypes);

        Comparator<Histogram> comparing = Comparator.comparing(fieldExtractor);
        List<Histogram> histograms =
            getHistograms(results, collectionTypeAndMark)
                .stream().sorted(comparing)
                .collect(Collectors.toList());

        Set<MethodType> methodTypes = getUniques(results, AveragedMethodResult::getMethodType);

        int lengthOfLongestCollectionName = MaxUtils.longest(histograms, Histogram::getCollectionType).length();
        int lengthOfLongestTime = MaxUtils.longest(histograms, Histogram::getAverageExecutionTime).length();
        int lengthOfLongestMethodName = MaxUtils.longest(methodTypes, MethodType::getValue).length();
        int lengthOfMax = MaxUtils.longest(histograms, fieldExtractor).length();

        StringBuilder builder = new StringBuilder();

        groupBy(Histogram::getMethodType, histograms)
            .forEach(histogramsByMethod -> {
                final StringBuilder builderForMethod = new StringBuilder();
                List<Histogram> histogramsByMethodValue = histogramsByMethod.getValue();

                groupBy(fieldExtractor, histogramsByMethodValue)
                    .forEach(histogramsByIndex -> {
                        final StringBuilder builderForIndex = new StringBuilder();
                        for (Histogram histogram : histogramsByIndex.getValue()) {
                            G groupingField = histogramsByIndex.getKey();
                            appendHistogram(
                                builderForIndex, groupingField, histogram,
                                lengthOfMax,
                                lengthOfLongestCollectionName,
                                lengthOfLongestMethodName,
                                lengthOfLongestTime);
                        }
                        builderForMethod.append(builderForIndex)
                            .append("-".repeat(lengthOfLongestMethodName))
                            .append("\n");
                    });
                builder.append(builderForMethod).append("\n");
            });

        return builder;
    }

    private static <T> void appendHistogram(
        final StringBuilder builderForIndex,
        final T groupingField,
        final Histogram histogram,
        final Integer lengthOfMaxGroupingField,
        final Integer lengthOfLongestCollectionName,
        final int lengthOfLongestMethodName,
        final Integer lengthOfLongestTime
    ) {
        String collectionTypeIndent = IndentUtils.getIndent(histogram.getCollectionType(), lengthOfLongestCollectionName);
        String executionTimeIndent = IndentUtils.getIndent(histogram.getAverageExecutionTime(), lengthOfLongestTime);
        String groupingFieldIndent = IndentUtils.getIndent(groupingField, lengthOfMaxGroupingField);
        String methodTypeIndent = IndentUtils.getIndent(histogram.getMethodType(), lengthOfLongestMethodName);

        builderForIndex
            .append(methodTypeIndent).append(histogram.getMethodType()).append(" ")
            .append(groupingFieldIndent).append(groupingField).append(" ")
            .append(collectionTypeIndent).append(histogram.getCollectionType()).append(" ")
            .append(executionTimeIndent).append(histogram.getAverageExecutionTime()).append(" ")
            .append("\n");
    }

}
