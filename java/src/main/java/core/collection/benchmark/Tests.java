package core.collection.benchmark;

import core.collection.benchmark.pojo.AveragedMethodResult;
import core.collection.benchmark.pojo.MethodResult;
import core.collection.benchmark.utils.HistogramWithIndexUtils;
import core.collection.benchmark.utils.HistogramWithNoIndexUtils;
import core.collection.benchmark.utils.MethodResultGroupingUtils;
import core.collection.benchmark.utils.Sequence;
import org.apache.commons.collections4.list.TreeList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static core.collection.benchmark.utils.CollectionCreationUtils.list;
import static core.collection.benchmark.utils.CollectionCreationUtils.set;
import static core.collection.benchmark.utils.ElementSupplier.getRandomElementAndForgetHimFrom;
import static core.collection.benchmark.utils.Sequence.intSequence;

public class Tests {
    private static final int size = 10_000;
    private static final int testsAmount = Math.min(size, 100);

    private static final int logStep = 100;
    private static final int maxHistogramLength = 50;

    public static void main(String[] args) {
        Sequence<Integer> hashSetSequence = intSequence();
        Sequence<Integer> treeSetSequence = intSequence();
        Sequence<Integer> linkedHashSetSequence = intSequence();
        Sequence<Integer> linkedListSequence = intSequence();
        Sequence<Integer> arrayListSequence = intSequence();
        Sequence<Integer> treeListSequence = intSequence();

        Set<Integer> hashSet       = set(HashSet.class,       size, hashSetSequence::next);
        Set<Integer> treeSet       = set(TreeSet.class,       size, treeSetSequence::next);
        Set<Integer> linkedHashSet = set(LinkedHashSet.class, size, linkedHashSetSequence::next);

        List<Integer> linkedList = list(LinkedList.class, size, linkedListSequence::next);
        List<Integer>  arrayList = list(ArrayList.class,  size, arrayListSequence::next);
        List<Integer>   treeList = list(TreeList.class,   size, treeListSequence::next);

        boolean enableLog = true;
        Map<Boolean, List<MethodResult>> partitionedResults = Stream.of(
                collectionTest(linkedList).    test(linkedListSequence::next,    randomFrom(hashSet),       enableLog, logStep),
                collectionTest(arrayList).     test(arrayListSequence::next,     randomFrom(treeSet),       enableLog, logStep),
                collectionTest(treeList).      test(treeListSequence::next,      randomFrom(linkedHashSet), enableLog, logStep),
                collectionTest(hashSet).       test(hashSetSequence::next,       randomFrom(linkedList),    enableLog, logStep),
                collectionTest(treeSet).       test(treeSetSequence::next,       randomFrom(arrayList),     enableLog, logStep),
                collectionTest(linkedHashSet). test(linkedHashSetSequence::next, randomFrom(treeList),      enableLog, logStep)
            )
            .flatMap(Collection::stream)
            .collect(Collectors.partitioningBy(it -> it.getIndex() != null));


        List<AveragedMethodResult> resultsWithIndex =
            MethodResultGroupingUtils.averageByIndex(partitionedResults.get(true));

        List<AveragedMethodResult> resultsWithNoIndex =
            MethodResultGroupingUtils.averageByMethod(partitionedResults.get(false));

        HistogramWithIndexUtils.printHistogram(resultsWithIndex);
        HistogramWithNoIndexUtils.printHistogram(resultsWithNoIndex);
    }

    private static <T> CollectionTest<T> collectionTest(final Collection<T> collection) {
        return new CollectionTest<>(testsAmount, collection);
    }

    private static <E> Supplier<E> randomFrom(final Collection<E> collection) {
        return getRandomElementAndForgetHimFrom(collection);
    }
}
