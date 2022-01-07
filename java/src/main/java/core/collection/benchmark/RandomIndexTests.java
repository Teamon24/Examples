package core.collection.benchmark;

import core.collection.benchmark.pojo.AveragedMethodResult;
import core.collection.benchmark.utils.HistogramWithIndexUtils;
import core.collection.benchmark.utils.Sequence;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static core.collection.benchmark.utils.CollectionCreationUtils.list;
import static core.collection.benchmark.utils.Sequence.intSequence;

public class RandomIndexTests {
    private static final int size = 50_000;
    private static final int testsAmount = 500_000;
    private static final int logStep = size;

    public static void main(String[] args) {
        List<AveragedMethodResult> result1 = test(ArrayList.class);
        List<AveragedMethodResult> result2 = test(LinkedList.class);


        List<AveragedMethodResult> filtered = Stream.of(result1, result2)
            .flatMap(Collection::stream)
            .filter(each(size / 10))
            .collect(Collectors.toList());

        StringBuilder histograms = HistogramWithIndexUtils.getStringHistograms(filtered);

        System.out.println(histograms);
    }

    private static Predicate<AveragedMethodResult> each(int number) {
        return it -> it.getIndex() % number == 0;
    }

    private static List<AveragedMethodResult> test(Class listClass) {
        Sequence<Integer> intSequence = intSequence();
        List<Integer> arrayList = list(listClass, size, intSequence::next);
        return new RandomIndexTestLogic(testsAmount, logStep)
            .test(intSequence, arrayList);
    }
}
