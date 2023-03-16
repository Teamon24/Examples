package core.collection.benchmark;

import core.collection.benchmark.core.CollectionTestBuilder;
import core.collection.benchmark.pojo.AveragedMethodResult;
import core.collection.benchmark.pojo.MethodResult;
import core.collection.benchmark.utils.CollectionCreationUtils;
import core.collection.benchmark.utils.CollectionSuppliers;
import core.collection.benchmark.utils.ElementSupplier;
import core.collection.benchmark.utils.HistogramWithIndexUtils;
import core.collection.benchmark.utils.IndexSuppliers;
import core.collection.benchmark.utils.IntSequence;
import core.collection.benchmark.utils.MethodResultGroupingUtils;
import core.collection.benchmark.utils.MethodsTestsTasks;
import core.collection.benchmark.utils.Sequence;
import utils.CallableUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class RandomIndexTests {
    private static final int size = 10_000;
    private static final int testsAmount = 100_000;

    private static final int logStep = testsAmount / 5;

    public static void main(String[] args) {

        List<MethodsTestsTasks<Integer>> methodResultsTasks = getMethodsTestsTasks(LinkedList.class, ArrayList.class);

        List<MethodResult<Integer>> methodResults =
            CallableUtils.getAll(methodResultsTasks, 5)
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

    private static List<MethodsTestsTasks<Integer>> getMethodsTestsTasks(Class<? extends List>... listClasses) {
        boolean enableLog = true;
        List<MethodsTestsTasks<Integer>> tests = new ArrayList<>();
        for (Class<?> listClass : listClasses) {
            Sequence<Integer> listSequence = IntSequence.create();
            tests.addAll(
                methodsTests(
                    testsAmount,
                    CollectionCreationUtils.createList(listClass, size, listSequence::next),
                    listSequence,
                    enableLog,
                    logStep)
            );
        }
        return tests;
    }

    public static <E> Predicate<AveragedMethodResult<E>> each(int number) {
        return it -> it.getIndex() % number == 0;
    }

    private static <E> List<MethodsTestsTasks<E>> methodsTests(
        final int testsAmount,
        final Collection<E> collection,
        final Sequence<E> newElementSequence,
        boolean enableLog,
        int logStep
    ) {

        return new CollectionTestBuilder<E>()
            .lists()
            .testsAmount(testsAmount)
            .collection(collection)
            .collectionSupplier(CollectionSuppliers.newCollection(collection))
            .newElementSupplier(newElementSequence::next)
            .existedElementSupplier(ElementSupplier.getElementSequentiallyFrom(collection))
            .indexSupplier(IndexSuppliers.supplyRandomIndex())
            .build()
            .callables()
            .getMethodsTests(enableLog, logStep);
    }
}
