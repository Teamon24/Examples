package core.collection.benchmark.utils;

import core.collection.benchmark.pojo.AveragedMethodResult;
import core.collection.benchmark.pojo.Histogram;
import core.collection.benchmark.pojo.MethodType;

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

    public static final int DOUBLE_ACCURACY = 2;
    static LinkedList<String> marks = new LinkedList<>();
    static HashMap<Object, String> usedMarks = new HashMap<>();

    static double maxHistogramColumnLength = 40;

    static {
        marks.add("*");
        marks.add("-");
        marks.add("o");
        marks.add("\\");
        marks.add("|");
        marks.add("+");
        marks.add("=");
        marks.add("#");
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

    public static <T> HashMap<T, String> mapToMarks(Set<T> ts){
        HashMap<T, String> tAndMark = new LinkedHashMap<>();

        Iterator<T> iterator = ts.iterator();
        for (int i = 0; iterator.hasNext();) {
            T t = iterator.next();
            String usedMark = usedMarks.get(t);
            if (usedMark == null) {
                String mark = marks.get(i);
                usedMarks.put(t, mark);
                tAndMark.put(t, mark);
                marks.remove(i);
            } else {
                tAndMark.put(t, usedMark);
            }
        }
        return tAndMark;
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

        if (minAverageTime != 0) {
            Double maxRelationAverageToMin = results.stream().map(it -> it.getAverageExecutionTime() / minAverageTime).max(Double::compareTo).get();
            List<Histogram> histograms = createHistogramsObjects(collectionTypeAndMark, results, minAverageTime, maxRelationAverageToMin);
            rescaleHistogramsColumns(collectionTypeAndMark, histograms);
            return histograms;
        } else {
            return createEmptyHistogramsObjects(results);
        }
    }


    private static void rescaleHistogramsColumns(final HashMap<String, String> collectionTypeAndMark,
                                                 final List<Histogram> histograms)
    {
        List<Histogram> sortedByAverageTime =
            histograms.stream()
                .sorted(Comparator.comparing(Histogram::getAverageExecutionTimeDouble)).toList();

        Histogram firstHistogram = sortedByAverageTime.get(0);
        firstHistogram.setHistogramColumn(collectionTypeAndMark.get(firstHistogram.getCollectionType()).repeat(1));
        for (int i = 1; i < sortedByAverageTime.size(); i++) {
            Histogram prevHistogram = sortedByAverageTime.get(i - 1);
            Histogram nextHistogram = sortedByAverageTime.get(i);
            String mark = collectionTypeAndMark.get(nextHistogram.getCollectionType());

            double averageExecutionTimeDouble = nextHistogram.getAverageExecutionTimeDouble();
            double averageExecutionTimeDouble1 = prevHistogram.getAverageExecutionTimeDouble();
            nextHistogram.setHistogramColumn(mark.repeat((int) (i* Math.min(2, averageExecutionTimeDouble / averageExecutionTimeDouble1 ))));
        }
    }

    private static List<Histogram> createEmptyHistogramsObjects(final List<AveragedMethodResult> results) {
        List<Histogram> histograms = results.stream().map(result -> {
            String collectionClass = result.getCollectionClass();
            return new Histogram(
                collectionClass,
                result.getMethodType(),
                result.getIndex(),
                "",
                "0.0");
        }).collect(Collectors.toList());
        return histograms;
    }

    public static <T> String getIndent(final T currentValue, final int maxValues) {
        return " ".repeat(maxValues - currentValue.toString().length());
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
                format(DOUBLE_ACCURACY, averageExecutionTime));
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

    public static String getSeparationLine(String separationSymbol, int count) {
        return separationSymbol.repeat(count);
    }


    public static Stream<Map.Entry<MethodType, List<Histogram>>> groupByMethod(final List<Histogram> histograms) {
        return histograms.stream()
            .collect(Collectors.groupingBy(Histogram::getMethodType))
            .entrySet()
            .stream();
    }

    public static <E> Double filterNotZeroAndFindBy(BiFunction<Stream<E>, Comparator<? super E>, Optional<E>> function,
                                                     final List<E> list,
                                                     Function<E, Double> fieldExtractor)
    {
        Optional<E> e = function.apply(
            list.stream().filter(it -> fieldExtractor.apply(it) != 0.0),
            Comparator.comparing(fieldExtractor)
        );
        return e.map(fieldExtractor).orElse(0.0);
    }

    public static String format(final int doubleAccuracy, final Double value) {
        return String.format("%." + doubleAccuracy + "f", value);
    }

    public static <T extends Comparable<T>> Stream<Map.Entry<T, List<Histogram>>> groupBy(
        final Function<Histogram, T> grouping,
        final List<Histogram> histograms)
    {
        return histograms.stream()
            .collect(Collectors.groupingBy(grouping))
            .entrySet()
            .stream()
            .sorted(Map.Entry.comparingByKey());
    }
}
