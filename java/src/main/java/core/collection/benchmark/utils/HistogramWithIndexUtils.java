package core.collection.benchmark.utils;

import core.collection.benchmark.pojo.AveragedMethodResult;
import core.collection.benchmark.pojo.Histogram;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static core.collection.benchmark.utils.MaxUtils.*;

public final class HistogramWithIndexUtils extends HistogramUtils {

    public static void printHistogram(final List<AveragedMethodResult> results,
                                      final Integer maxHistogramLength)
    {
        maxHistogramColumnLength = maxHistogramLength;
        StringBuilder stringBuilder = getStringHistograms(results);
        System.out.println(stringBuilder);
    }

    public static StringBuilder getStringHistograms(final List<AveragedMethodResult> results,
                                                    final Integer maxHistogramLength)
    {
        maxHistogramColumnLength = maxHistogramLength;
        return getStringHistograms(results);
    }

    private static StringBuilder getStringHistograms(List<AveragedMethodResult> results) {
        if (results.isEmpty()) {
            System.out.println("No results");
            return null;
        }
        Set<String> collectionTypes = StreamUtils.getUniques(results, AveragedMethodResult::getCollectionClass);
        checkSize(collectionTypes);

        HashMap<String, String> collectionTypeAndMark = mapToMarks(collectionTypes);

        List<Histogram> histograms =
            getHistograms(results, collectionTypeAndMark)
                .stream()
                .sorted(Comparator.comparing(Histogram::getIndex))
                .collect(Collectors.toList());

        int lengthOfLongestCollectionName = longestBy(histograms, Histogram::getCollectionType).length();
        int lengthOfMaxIndex              = longestBy(histograms, Histogram::getIndex).length();
        int lengthOfLongestTime           = longestBy(histograms, Histogram::getAverageExecutionTime).length();
        int lengthOfLongestHistogram      = longestBy(histograms, Histogram::getHistogramColumn).length();
        int lengthOfLongestMethodName     = longestBy(histograms, Histogram::getMethodType).length();

        StringBuilder builder = new StringBuilder();

        groupBy(Histogram::getMethodType, histograms)
            .forEach(histogramsByMethod -> {
                final StringBuilder builderForMethod = new StringBuilder();
                List<Histogram> histogramsByMethodValue = histogramsByMethod.getValue();

                groupBy(Histogram::getIndex, histogramsByMethodValue)
                    .forEach(histogramsByIndex -> {
                        final StringBuilder builderForIndex = new StringBuilder();
                        for (Histogram histogram : histogramsByIndex.getValue()) {
                            Integer index = histogramsByIndex.getKey();
                            appendHistogram(
                                builderForIndex, index, histogram,
                                lengthOfMaxIndex,
                                lengthOfLongestCollectionName,
                                lengthOfLongestHistogram,
                                lengthOfLongestMethodName,
                                lengthOfLongestTime);
                        }
                        builderForMethod.append(builderForIndex);
                    });
                builder.append(builderForMethod).append("\n");
            });

        return builder;
    }

    private static void appendHistogram(final StringBuilder builderForIndex,
                                        final Integer index,
                                        final Histogram histogram,
                                        final Integer lengthOfMaxIndex,
                                        final Integer lengthOfLongestCollectionName,
                                        final int lengthOfLongestHistogram,
                                        final int lengthOfLongestMethodName,
                                        final Integer lengthOfLongestTime)
    {
        String histogramColumnIndent = getIndent(histogram.getHistogramColumn(), lengthOfLongestHistogram);
        String collectionTypeIndent = getIndent(histogram.getCollectionType(), lengthOfLongestCollectionName);
        String executionTimeIndent = getIndent(histogram.getAverageExecutionTime(), lengthOfLongestTime);
        String indexIndent = getIndent(index, lengthOfMaxIndex);
        String methodTypeIndent = getIndent(histogram.getMethodType(), lengthOfLongestMethodName);

        builderForIndex
            .append(methodTypeIndent).append(histogram.getMethodType()).append(" ")
            .append(indexIndent).append(index).append(" ")
            .append(collectionTypeIndent).append(histogram.getCollectionType()).append(" ")
            .append(histogram.getHistogramColumn()).append(histogramColumnIndent).append(" ")
            .append(executionTimeIndent).append(histogram.getAverageExecutionTime()).append(" ")
            .append("\n");
    }

}
