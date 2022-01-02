package core.concurrency.thread.ex1;

import core.utils.TableUtils;
import core.concurrency.thread.ThreadUtils;
import core.concurrency.thread.ex1.state.StateObject;
import core.concurrency.thread.ex1.state.StateObjectImpl;
import core.concurrency.thread.ex1.state.SynchronizedStateObject;
import core.concurrency.thread.ex1.state.VolatileStateObject;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        int doubleAccuracy = 2;
        int threadsAmount = 10;
        final StateObject stateObject1 = new StateObjectImpl();
        final StateObject stateObject2 = new VolatileStateObject();
        final StateObject stateObject3 = new SynchronizedStateObject();

        int expected = Counter.LIMIT;
        final List<TableUtils.Record> records = new ArrayList<>();
        Stream
            .of(stateObject1, stateObject2, stateObject3)
            .forEach(
                stateObject -> {
                    int actual = increment(threadsAmount, stateObject);
                    TableUtils.Record record = getRecord(stateObject, doubleAccuracy, threadsAmount, expected, actual);
                    records.add(record);
                });

        final TableUtils.Table table =
            new TableUtils.Table(List.of("Case", "Threads", "Expected", "Actual", "Lost"), records);
        TableUtils.printResults(table);
    }

    private static TableUtils.Record getRecord(final StateObject stateObject,
                                               final int doubleAccuracy,
                                               final int threadsAmount,
                                               final int expected,
                                               final int actual)
    {
        return new TableUtils.Record(
            new HashMap<>() {{
                put("Expected", String.valueOf(expected));
                put("Actual", String.valueOf(actual));
                put("Threads", String.valueOf(threadsAmount));
                put("Lost", String.valueOf(getLossString(actual, expected, doubleAccuracy)));
                put("Case", stateObject.getClass().getSimpleName());
            }}
        );
    }

    private static int increment(int threadsAmount,
                                 final StateObject stateObject)
    {
        Counter checker = new Counter();
        Function<Integer, Thread> integerThreadFunction = (Integer id) -> new IncrementThread(checker, stateObject);
        final List<Thread> threads = ThreadUtils.createThreads(threadsAmount, integerThreadFunction);
        threads.forEach(Thread::start);
        ThreadUtils.join(threads);
        return stateObject.getI();
    }

    private static HashMap<Pair<String, Integer>, String> table(final int threadsAmount,
                                                 final int expected,
                                                 final int actual,
                                                 final int doubleAccuracy)
    {
        return new HashMap<>() {{
            put(Pair.of("Threads", 1), String.valueOf(threadsAmount));
            put(Pair.of("Expected", 2), String.valueOf(expected));
            put(Pair.of("Actual", 3), String.valueOf(actual));
            put(Pair.of("Lost", 4), getLossString(actual, expected, doubleAccuracy));
        }};
    }

    private static String getLossString(final double actual, double expected, int doubleAccuracy) {
        Double loss = 100 - actual / expected * 100;

        String lossStringTemplate = String.format("!.%sf", doubleAccuracy).replace("!", "%");
        return String.format(lossStringTemplate, loss);
    }

}
