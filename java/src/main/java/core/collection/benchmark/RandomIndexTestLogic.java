package core.collection.benchmark;

import core.collection.benchmark.pojo.AveragedMethodResult;
import core.collection.benchmark.pojo.MethodResult;
import core.collection.benchmark.utils.MethodResultGroupingUtils;
import core.collection.benchmark.utils.Sequence;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public record RandomIndexTestLogic(int testsAmount, int logStep) {

    public <T> List<AveragedMethodResult> test(Sequence<T> sequence, List<T> list) {

        Map<Boolean, List<MethodResult>> partitionedResults =
            collectionTest(list)
                .testRandomIndex(sequence::next, logStep)
                .stream()
                .collect(Collectors.partitioningBy(it -> it.getIndex() != null));


        List<AveragedMethodResult> resultsWithIndex =
            MethodResultGroupingUtils.averageByIndex(partitionedResults.get(true));


        return resultsWithIndex;
    }

    private <T> CollectionTest<T> collectionTest(final Collection<T> collection) {
        return new CollectionTest<>(testsAmount, collection);
    }

}
