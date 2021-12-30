package benchmark.collection.utils;

import benchmark.collection.pojo.AveragedMethodResult;
import benchmark.collection.pojo.MethodResult;
import benchmark.collection.pojo.MethodType;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class MethodResultGroupingUtils {


    public static List<AveragedMethodResult> averageByIndex(List<MethodResult> testResults) {

        Map<String, Map<MethodType, Map<Integer, Double>>> averageExecutionTime = testResults.stream().collect(
            Collectors.groupingBy(
                MethodResult::getCollectionClass,
                Collectors.groupingBy(
                    MethodResult::getMethodType,
                    Collectors.groupingBy(
                        MethodResult::getIndex,
                        Collectors.averagingLong(
                            MethodResult::getExecutionTime)))));


        List<AveragedMethodResult> collect = averageExecutionTime.entrySet()
            .stream()
            .flatMap(entry -> getStream(entry)
                .flatMap(entry2 -> getEntryStream(entry2)
                    .flatMap(entry3 -> {
                        String collectionType = entry.getKey();
                        return Stream.of(getAveragedMethodResult(collectionType, entry2, entry3));
                    })))
            .collect(Collectors.toList());

        return collect;
    }

    private static <T> Stream<Map.Entry<T, Double>>
    getEntryStream(final Map.Entry<MethodType, Map<T, Double>> entry) {
        return entry.getValue().entrySet().stream();
    }

    private static <T> Stream<Map.Entry<MethodType, Map<T, Double>>>
    getStream(final Map.Entry<String, Map<MethodType, Map<T, Double>>> entry) {
        return entry.getValue().entrySet().stream();
    }

    private static <T> AveragedMethodResult getAveragedMethodResult(
        String collectionType, final Map.Entry<MethodType, Map<T, Double>> entry2,
        final Map.Entry<T, Double> entry3)
    {
        MethodType methodType = entry2.getKey();
        T key = entry3.getKey();
        Double averagedTime = entry3.getValue();
        if (!collectionType.contains("Set")) {
            return new AveragedMethodResult(collectionType, methodType, (Integer) key, null, averagedTime);
        } else {
            return new AveragedMethodResult(collectionType, methodType, null, key, averagedTime);
        }
    }

    public static List<AveragedMethodResult> averageByMethod(List<MethodResult> testResults) {

        Map<String, Map<MethodType, Double>> averageExecutionTime = testResults.stream().collect(
            Collectors.groupingBy(
                MethodResult::getCollectionClass,
                Collectors.groupingBy(
                    MethodResult::getMethodType,
                    Collectors.averagingLong(
                        MethodResult::getExecutionTime))));


        List<AveragedMethodResult> collect = averageExecutionTime.entrySet()
            .stream()
            .flatMap(collectionMethodsAndAverageTime -> getStream1(collectionMethodsAndAverageTime)
                .flatMap(methodAndAverageTime -> {
                    String collectionType = collectionMethodsAndAverageTime.getKey();
                    return Stream.of(getAveragedMethodResult1(collectionType, methodAndAverageTime));
                }))
            .collect(Collectors.toList());

        return collect;
    }

    private static <T> Stream<Map.Entry<MethodType, Double>>
    getStream1(final Map.Entry<String, Map<MethodType, Double>> entry) {
        return entry.getValue().entrySet().stream();
    }

    private static <T> AveragedMethodResult getAveragedMethodResult1(
        String collectionType, final Map.Entry<MethodType, Double> methodAndAverageTime)
    {
        MethodType methodType = methodAndAverageTime.getKey();
        Double averagedTime = methodAndAverageTime.getValue();
        return new AveragedMethodResult(collectionType, methodType, null, null, averagedTime);
    }

}
