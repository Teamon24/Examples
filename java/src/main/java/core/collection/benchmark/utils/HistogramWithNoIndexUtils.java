package core.collection.benchmark.utils;

import core.collection.benchmark.pojo.AveragedMethodResult;
import core.collection.benchmark.pojo.Histogram;
import core.collection.benchmark.pojo.MethodType;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import static core.collection.benchmark.utils.MaxUtils.*;
import static core.collection.benchmark.utils.StreamUtils.*;

public final class HistogramWithNoIndexUtils extends HistogramUtils {

    public static void printHistogram(List<AveragedMethodResult> results) {
        if (results.isEmpty()) {
            System.out.println("No results");
            return;
        }
        Set<String> collectionTypes = getUniques(results, AveragedMethodResult::getCollectionClass);
        checkSize(collectionTypes);

        HashMap<String, String> collectionTypeAndMark = mapToMarks(collectionTypes);

        List<Histogram> histograms = getHistograms(results, collectionTypeAndMark);

        Integer longestTimeLength = longestBy(histograms, Histogram::getAverageExecutionTime).length();
        Integer longestCollectionNameLength = longest(collectionTypes).length();
        int lengthOfLongestHistogram = longestBy(histograms, Histogram::getHistogramColumn).length();

        Set<MethodType> methodTypes = getUniques(results, AveragedMethodResult::getMethodType);
        int longestMethodNameLength = longestBy(methodTypes, MethodType::getValue).length();

        StringBuilder builder = new StringBuilder();
        groupByMethod(histograms)
            .forEach(histogramsByMethod -> {
                final StringBuilder builderForMethod = new StringBuilder();
                for (Histogram histogram : histogramsByMethod.getValue()) {
                    appendHistogram(
                        builderForMethod, histogram,
                        longestCollectionNameLength,
                        lengthOfLongestHistogram,
                        longestMethodNameLength,
                        longestTimeLength);
                }
                builder.append(builderForMethod).append("\n");
            });

        System.out.println(builder);
    }

    private static void appendHistogram(final StringBuilder builderForMethod,
                                        final Histogram histogram,
                                        final Integer lengthOfLongestCollectionName,
                                        final int lengthOfLongestHistogram,
                                        final int lengthOfLongestMethodName,
                                        final Integer lengthOfLongestTime)
    {
        String histogramColumnIndent = getIndent(histogram.getHistogramColumn(), lengthOfLongestHistogram);
        String collectionTypeIndent = getIndent(histogram.getCollectionType(), lengthOfLongestCollectionName);
        String executionTimeIndent = getIndent(histogram.getAverageExecutionTime(), lengthOfLongestTime);
        String methodTypeIndent = getIndent(histogram.getMethodType(), lengthOfLongestMethodName);

        builderForMethod
            .append(methodTypeIndent).append(histogram.getMethodType()).append(" ")
            .append(collectionTypeIndent).append(histogram.getCollectionType()).append(" ")
            .append(histogram.getHistogramColumn()).append(histogramColumnIndent).append(" ")
            .append(executionTimeIndent).append(histogram.getAverageExecutionTime()).append(" ")
            .append("\n");
    }
}
