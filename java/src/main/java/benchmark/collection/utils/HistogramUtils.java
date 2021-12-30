package benchmark.collection.utils;

import benchmark.collection.pojo.AveragedMethodResult;
import benchmark.collection.pojo.Histogram;
import benchmark.collection.pojo.MethodType;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class HistogramUtils {

    static LinkedList<String> marks = new LinkedList<>();
    static HashMap<String, String> usedMarks = new HashMap<>();

    static double maxHistogramColumnLength = 40;

    static {
        marks.add("*");
        marks.add("#");
        marks.add("+");
        marks.add("=");
        marks.add("|");
        marks.add("\\");
        marks.add("o");
        marks.add("0");
    }

    public static void checkSize(final Set<String> collectionTypes) {
        HashSet<String> restCollectionTypes = new HashSet<>(collectionTypes);
        restCollectionTypes.removeAll(usedMarks.keySet());
        int resultsSize = restCollectionTypes.size();
        int marksSize = marks.size();
        if (resultsSize > marksSize) {
            String template = "Not enough marks for collection types: marks amount - %s, but collection amount - %s";
            String message = String.format(template, marksSize, resultsSize);
            throw new RuntimeException(message);
        }
    }

    public static HashMap<String, String> mapTypesToMarks(Set<String> collectionTypes){
        HashMap<String, String> classToSign = new LinkedHashMap<>();

        Iterator<String> iterator = collectionTypes.iterator();
        for (int i = 0; iterator.hasNext();) {
            String collectionType = iterator.next();
            String mark = usedMarks.get(collectionType);
            if (mark == null) {
                String value = marks.get(i);
                usedMarks.put(collectionType, value);
                classToSign.put(collectionType, value);
                marks.remove(i);
            } else {
                classToSign.put(collectionType, mark);
            }
        }
        return classToSign;
    }

    public static List<Histogram> getHistograms(final List<AveragedMethodResult> results,
                                                final HashMap<String, String> collectionTypeAndMark)
    {
        Double minAverageTime = results.stream()
            .map(it -> getDigitsAfterDot(it.getAverageExecutionTime()))
            .filter(it -> it != 0)
            .min(Double::compareTo).orElseGet( () ->
                filterNotZeroAndFindBy(Stream::min, results, AveragedMethodResult::getAverageExecutionTime)
            );

        Double maxRelationAverageToMin = results.stream().map(it -> it.getAverageExecutionTime()/minAverageTime).max(Double::compareTo).get();
        List<Histogram> histograms = createHistogramsObjects(collectionTypeAndMark, results, minAverageTime, maxRelationAverageToMin);
        return histograms;
    }

    public static String getHistogramColumnIndent(final Histogram histogram, final int lengthOfLongestHistogram) {
        return " ".repeat(lengthOfLongestHistogram - histogram.getHistogramColumn().length());
    }

    public static String getCollectionTypeIndent(final String collectionType, final Integer lengthOfLongestCollectionName) {
        return " ".repeat(lengthOfLongestCollectionName - collectionType.length());
    }

    public static List<Histogram> createHistogramsObjects(final HashMap<String, String> collectionTypeAndMark,
                                                          final List<AveragedMethodResult> groupedByMethod,
                                                          final Double minAverageTime,
                                                          final Double maxRelationAverageToMin)
    {
        List<Histogram> histograms = groupedByMethod.stream().map(result -> {
            String collectionClass = result.getCollectionClass();
            Double averageExecutionTime = result.getAverageExecutionTime();
            return new Histogram(
                collectionClass,
                result.getMethodType(),
                result.getIndex(),
                getHistogramColumn(collectionClass, collectionTypeAndMark, averageExecutionTime, minAverageTime, maxRelationAverageToMin),
                averageExecutionTime);
        }).collect(Collectors.toList());
        return histograms;
    }

    public static String getHistogramColumn(final String collectionClass,
                                            final HashMap<String, String> collectionTypeAndMark,
                                            final Double averageExecutionTime,
                                            final Double minAverageTime,
                                            final Double maxRelationAverageToMin)
    {
        String mark = collectionTypeAndMark.get(collectionClass);
        if (averageExecutionTime == 0) return "";
        double v = maxHistogramColumnLength * (averageExecutionTime / minAverageTime) / maxRelationAverageToMin;
        if (v < 1 && v != 0) v = 1;
        int rounded = Math.round((int) v);
        return mark.repeat(rounded);
    }

    public static double getDigitsAfterDot(double d) {
        BigDecimal bd = new BigDecimal(d - Math.floor(d));
        bd = bd.setScale(4, RoundingMode.HALF_DOWN);
        return bd.doubleValue();
    }

    public static double getDigitsBeforeDot(double d) {
        BigDecimal bd = new BigDecimal(d - Math.floor(d));
        bd = bd.setScale(4, RoundingMode.HALF_DOWN);
        return bd.doubleValue();
    }

    public static String getSeparationLine(String separationSymbol, int count) {
        return separationSymbol.repeat(count);
    }


    public static Stream<Map.Entry<MethodType, List<Histogram>>> groupByMethod(final List<Histogram> histograms) {
        return histograms.stream()
            .collect(Collectors.groupingBy(Histogram::getMethodType)).entrySet().stream();
    }

    public static <E> Double filterNotZeroAndFindBy(BiFunction<Stream<E>, Comparator<? super E>, Optional<E>> function,
                                                     final List<E> list,
                                                     Function<E, Double> fieldExtractor)
    {
        E e = function.apply(
            list.stream().filter(it -> fieldExtractor.apply(it) != 0.0),
            Comparator.comparing(fieldExtractor)
        ).get();
        return fieldExtractor.apply(e);
    }

}
