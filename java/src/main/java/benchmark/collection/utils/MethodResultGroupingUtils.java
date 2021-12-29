package benchmark.collection.utils;

import benchmark.collection.pojo.AveragedMethodResult;
import benchmark.collection.pojo.MethodResult;
import benchmark.collection.pojo.MethodType;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class MethodResultGroupingUtils {

    public static <T> List<AveragedMethodResult> getAverageExecutionTime(List<MethodResult> testResults,
                                                                         Function<MethodResult, T> grouping) {

        Map<String, Map<MethodType, Map<T, Double>>> averageExecutionTime = testResults.stream().collect(
            Collectors.groupingBy(
                MethodResult::getCollectionClass,
                Collectors.groupingBy(
                    MethodResult::getMethodType,
                    Collectors.groupingBy(
                        grouping,
                        Collectors.averagingLong(
                            MethodResult::getExecutionTime)))));


        List<AveragedMethodResult> collect = averageExecutionTime.entrySet()
            .stream()
            .flatMap(entry -> getStream(entry)
                .flatMap(entry2 -> getEntryStream(entry2)
                    .flatMap(entry3 -> Stream.of(getAveragedMethodResult(entry, entry2, entry3)))))
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
        final Map.Entry<String, Map<MethodType, Map<T, Double>>> entry,
        final Map.Entry<MethodType, Map<T, Double>> entry2,
        final Map.Entry<T, Double> entry3)
    {
        String collectionType = entry.getKey();
        MethodType methodType = entry2.getKey();
        T key = entry3.getKey();
        Double averagedTime = entry3.getValue();
        if (!collectionType.contains("Set")) {
            return new AveragedMethodResult(collectionType, methodType, (Integer) key, null, averagedTime);
        } else {
            return new AveragedMethodResult(collectionType, methodType, null, key, averagedTime);
        }
    }
}
