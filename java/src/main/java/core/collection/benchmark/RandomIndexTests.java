package core.collection.benchmark;

import core.collection.benchmark.pojo.AveragedMethodResult;
import core.collection.benchmark.pojo.MethodResult;
import core.collection.benchmark.utils.HistogramWithIndexUtils;
import core.collection.benchmark.utils.MethodResultGroupingUtils;
import core.collection.benchmark.utils.Sequence;
import utils.ConcurrencyUtils;
import org.apache.commons.collections4.list.TreeList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static core.collection.benchmark.utils.CollectionCreationUtils.list;
import static core.collection.benchmark.utils.CollectionSuppliers.newCollection;
import static core.collection.benchmark.utils.ElementSupplier.getElementAndDiscard;

public class RandomIndexTests {
    private static final int size = 1_000;
    private static final int testsAmount = 10_000;

    private static final int logStep = testsAmount / 5;

    public static void main(String[] args) {
        Sequence<Integer> linkedListSequence = Sequence.intSequence();
        Sequence<Integer> arrayListSequence = Sequence.intSequence();
        Sequence<Integer> treeListSequence = Sequence.intSequence();

        List<Integer> linkedList = list(LinkedList.class, size, linkedListSequence::next);
        List<Integer> arrayList = list(ArrayList.class, size, arrayListSequence::next);
        List<Integer> treeList = list(TreeList.class, size, treeListSequence::next);

        boolean enableLog = true;
        List<List<Callable<List<MethodResult<Integer>>>>> methodResultsTasks = List.of(
            test(testsAmount, linkedList, linkedListSequence).getRandomIndexListTests(enableLog, logStep),
            test(testsAmount, arrayList, arrayListSequence).getRandomIndexListTests(enableLog, logStep),
            test(testsAmount, treeList, treeListSequence).getRandomIndexListTests(enableLog, logStep)
        );

        List<List<MethodResult<Integer>>> methodResultsList = ConcurrencyUtils.getAll(
            methodResultsTasks.stream().flatMap(Collection::stream).collect(Collectors.toList())
        );

        List<MethodResult<Integer>> methodResults = methodResultsList
            .stream()
            .flatMap(Collection::stream)
            .collect(Collectors.toList());

        List<AveragedMethodResult<Integer>> resultsWithIndex =
            MethodResultGroupingUtils
                .averageByIndex(methodResults)
                .stream()
                .filter(each(size / 3))
                .collect(Collectors.toList());

        HistogramWithIndexUtils.printHistogram(resultsWithIndex);
    }

    public static <E> Predicate<AveragedMethodResult<E>> each(int number) {
        return it -> it.getIndex() % number == 0;
    }

    private static <E> CallableCollectionTest<E> test(
        final int testsAmount,
        final Collection<E> collection,
        final Sequence<E> sequence
    ) {
        CollectionTest<E> collectionTest = new CollectionTest.CollectionTestBuilder<E>()
            .testsAmount(testsAmount)
            .collection(collection)
            .collectionSupplier(newCollection(collection))
            .newElementSupplier(sequence::next)
            .existedElementSupplier(getElementAndDiscard(collection))
            .build();

        return new CallableCollectionTest<>(collectionTest);
    }
}
