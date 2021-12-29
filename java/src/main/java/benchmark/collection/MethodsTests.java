package benchmark.collection;

import benchmark.collection.pojo.AveragedMethodResult;
import benchmark.collection.pojo.MethodResult;
import benchmark.collection.pojo.Result;
import benchmark.collection.utils.HistogramUtils;
import benchmark.collection.utils.Sequence;
import org.apache.commons.collections4.list.TreeList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static benchmark.collection.strategies.AddStrategies.addFirstStrategy;
import static benchmark.collection.strategies.AddStrategies.addLastStrategy;
import static benchmark.collection.strategies.AddStrategies.addMiddleStrategy;
import static benchmark.collection.strategies.AddStrategies.addStrategy;
import static benchmark.collection.strategies.GetStrategies.getFirstStrategy;
import static benchmark.collection.strategies.GetStrategies.getLastStrategy;
import static benchmark.collection.strategies.GetStrategies.getMiddleStrategy;
import static benchmark.collection.strategies.RemoveStrategies.*;
import static benchmark.collection.utils.CollectionCreationUtils.fillArrayList;
import static benchmark.collection.utils.CollectionCreationUtils.fillHashSet;
import static benchmark.collection.utils.CollectionCreationUtils.fillLinkedHashSet;
import static benchmark.collection.utils.CollectionCreationUtils.fillLinkedList;
import static benchmark.collection.strategies.SetStrategies.setFirstStrategy;
import static benchmark.collection.strategies.SetStrategies.setLastStrategy;
import static benchmark.collection.strategies.SetStrategies.setMiddleStrategy;
import static benchmark.collection.utils.CollectionCreationUtils.fillTreeList;
import static benchmark.collection.utils.CollectionCreationUtils.fillTreeSet;
import static benchmark.collection.utils.MethodResultGroupingUtils.*;
import static benchmark.collection.utils.PrintUtils.printTable;

public class MethodsTests {
    private static final int size = 1_000_000;
    private static final int testsAmount = 10;

    public static void main(String[] args) {
        Sequence<Integer> intSequence = Sequence.first(0).init((it -> it++));
        Sequence<Double> doubleSequence = Sequence.first(0.0).init((it -> it++));

        Set<Integer> hashSet = fillHashSet(size, intSequence::next);
        Set<Integer> treeSet = fillTreeSet(size, intSequence::next);
        Set<Integer> linkedHashSet = fillLinkedHashSet(size, intSequence::next);

        LinkedList<Double> linkedList = fillLinkedList(size, doubleSequence::next);
        ArrayList<Integer> arrayList = fillArrayList(size, intSequence::next);
        TreeList<Double> treeList = fillTreeList(size, doubleSequence::next);

        Map<Boolean, List<AveragedMethodResult>> partitionedResults = Stream.of(
                testList(1.0, linkedList),
                testList(1, arrayList),
                testList(1.0, treeList)
//                testSet(hashSet, 1),
//                testSet(hashSet, size / 2),
//                testSet(hashSet, size - 1),
//                testSet(treeSet, 1),
//                testSet(treeSet, size / 2),
//                testSet(treeSet, size - 1),
//                testSet(linkedHashSet, 1),
//                testSet(linkedHashSet, size / 2),
//                testSet(linkedHashSet, size - 1)
            )
            .flatMap(Collection::stream).collect(Collectors.partitioningBy(it -> it.getIndex() != null));

        List<AveragedMethodResult> listsResults = partitionedResults.get(true);
        List<AveragedMethodResult> setResults = partitionedResults.get(false);



        printTable(
            listsResults.stream().sorted(
                Comparator
                    .comparing(AveragedMethodResult::getMethodType)
                    .thenComparing(AveragedMethodResult::getIndex)
                    .thenComparing(AveragedMethodResult::getAverageExecutionTime)).collect(Collectors.toList()));

        printTable(
            setResults.stream().sorted(
            Comparator
                .comparing(AveragedMethodResult::getMethodType)
                .thenComparing(AveragedMethodResult::getAverageExecutionTime)).collect(Collectors.toList()));

        //Добавить Supplier для получения индекса в класс
        HistogramUtils.printHistogramForLists(listsResults);
    }

    private static <E> List<AveragedMethodResult> testList(final E element,
                                                           final List<E> list)
    {
        List<MethodTest> methodTests = List.of(
            new MethodTest(testsAmount, removeFirstStrategy(list)),
            new MethodTest(testsAmount, removeMiddleStrategy(list)),
            new MethodTest(testsAmount, removeLastStrategy(list)),

            new MethodTest(testsAmount, addFirstStrategy(list, element)),
            new MethodTest(testsAmount, addMiddleStrategy(list, element)),
            new MethodTest(testsAmount, addLastStrategy(list, element)),

            new MethodTest(testsAmount, getFirstStrategy(list)),
            new MethodTest(testsAmount, getMiddleStrategy(list)),
            new MethodTest(testsAmount, getLastStrategy(list)),

            new MethodTest(testsAmount, setFirstStrategy(list, element)),
            new MethodTest(testsAmount, setMiddleStrategy(list, element)),
            new MethodTest(testsAmount, setLastStrategy(list, element))
        );

        return test(methodTests,
            testResults -> getAverageExecutionTime(testResults, MethodResult::getIndex),
                    Comparator
                        .comparing(Result::getMethodType)
                        .thenComparing(Result::getIndex));
    }

    private static <E> List<AveragedMethodResult> testSet(final Set<E> set,
                                                          final E element)
    {
        MethodTest removeMethodTest = new MethodTest(testsAmount, removeStrategy(set, element));
        MethodTest addMethodTest = new MethodTest(testsAmount, addStrategy(set, element));

        List<MethodTest> methodTests = List.of(removeMethodTest, addMethodTest);
        final List<AveragedMethodResult> setResults = test(
            methodTests,
            testResults -> getAverageExecutionTime(testResults, MethodResult::getElement),
            Comparator.comparing(Result::getMethodType)
        );

        return setResults;
    }

    private static List<AveragedMethodResult> test(
        final List<MethodTest> methodTests,
        final Function<List<MethodResult>, List<AveragedMethodResult>> averaging,
        final Comparator<Result> comparator
    ) {
        List<MethodResult> collect = methodTests.stream()
            .map(it -> it.test(true, 100))
            .flatMap(Collection::stream)
            .collect(Collectors.toList());

        return averaging.apply(collect)
            .stream()
            .sorted(comparator)
            .collect(Collectors.toList());
    }

}
