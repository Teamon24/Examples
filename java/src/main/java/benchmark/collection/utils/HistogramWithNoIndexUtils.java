package benchmark.collection.utils;

import benchmark.collection.pojo.AveragedMethodResult;
import benchmark.collection.pojo.Histogram;
import benchmark.collection.pojo.MethodType;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import static benchmark.collection.utils.StreamUtils.getUniques;

public final class HistogramWithNoIndexUtils extends HistogramUtils {

    public static void printHistogram(final List<AveragedMethodResult> results, final int maxHistogramLength) {
        maxHistogramColumnLength = maxHistogramLength;
        printHistogram(results);
    }

    public static void printHistogram(List<AveragedMethodResult> results) {
        Set<String> collectionTypes = getUniques(results, AveragedMethodResult::getCollectionClass);
        checkSize(collectionTypes);

        HashMap<String, String> collectionTypeAndMark = mapTypesToMarks(collectionTypes);
        List<Histogram> histograms = getHistograms(results, collectionTypeAndMark);

        Integer lengthOfLongestCollectionName = MaxUtils.getLongestCollectionName(collectionTypes).length();
        int lengthOfLongestHistogram = MaxUtils.getLongestHistogramColumn(histograms).length();
        Set<MethodType> methodTypes = getUniques(results, AveragedMethodResult::getMethodType);
        int lengthOfLongestMethodName = MaxUtils.getLongestMethodName(methodTypes).length();

        int count = 2 * (
            lengthOfLongestMethodName +
                lengthOfLongestCollectionName +
                lengthOfLongestCollectionName);

        StringBuilder builder = new StringBuilder();
        groupByMethod(histograms)
            .forEach(histogramsByMethod -> {
                final StringBuilder builderForMethod = new StringBuilder();
                appendFirstLine(count, builderForMethod);
                for (Histogram histogram : histogramsByMethod.getValue()) {
                    appendHistogram(
                        builderForMethod, histogram,
                        lengthOfLongestCollectionName,
                        lengthOfLongestHistogram,
                        lengthOfLongestMethodName);
                }
                appendMethodGroup(builder, builderForMethod);
            });

        System.out.println(builder);
    }

    private static void appendMethodGroup(final StringBuilder builder, final StringBuilder builderForMethod) {
        builder
            .append(builderForMethod)
            .append("\n");
    }

    private static void appendFirstLine(final int count, final StringBuilder builderForMethod) {
        builderForMethod.append(getSeparationLine("-", count)).append("\n");
    }

    private static void appendHistogram(final StringBuilder builderForIndex,
                                        final Histogram histogram,
                                        final Integer lengthOfLongestCollectionName,
                                        final int lengthOfLongestHistogram,
                                        final int lengthOfLongestMethodName)
    {
        int counter = 0;
        String collectionType = histogram.getCollectionType();
        String methodTypeName = histogram.getMethodType().getValue();
        String histogramColumnIndent = getHistogramColumnIndent(histogram, lengthOfLongestHistogram);
        String collectionTypeIndent = getCollectionTypeIndent(collectionType, lengthOfLongestCollectionName);
        if (counter == 0) {
            builderForIndex
                .append(" ".repeat(lengthOfLongestMethodName - methodTypeName.length()))
                .append(methodTypeName)
                .append(" ");

            counter++;
        } else {
            builderForIndex.append(" ".repeat(lengthOfLongestMethodName)).append(" ");
        }
        appendIndexLine(builderForIndex, histogram, collectionTypeIndent, histogramColumnIndent);
        builderForIndex.append("\n");
    }

    private static StringBuilder appendIndexLine(final StringBuilder builderForIndex,
                                                 final Histogram it,
                                                 final String collectionTypeIndent,
                                                 final String histogramColumnIndent) {
        return builderForIndex
            .append(collectionTypeIndent).append(it.getCollectionType()).append(" ")
            .append(it.getHistogramColumn()).append(histogramColumnIndent).append(" ")
            .append(it.getAverageExecutionTime()).append(" ");
    }

}
