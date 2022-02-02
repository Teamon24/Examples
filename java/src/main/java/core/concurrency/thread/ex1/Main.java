package core.concurrency.thread.ex1;

import utils.TableUtils;
import utils.ConcurrencyUtils;
import core.concurrency.thread.ex1.state.State;
import core.concurrency.thread.ex1.state.StateImpl;
import core.concurrency.thread.ex1.state.SynchronizedState;
import core.concurrency.thread.ex1.state.VolatileState;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import static utils.ClassUtils.simpleName;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        int doubleAccuracy = 3;
        int threadsAmount = 5;

        final State state1 = new StateImpl();
        final State state2 = new VolatileState();
        final State state3 = new SynchronizedState();

        int expected = Counter.LIMIT;
        final List<TableUtils.Record> records = new ArrayList<>();
        Stream
            .of(state1, state2, state3)
            .forEach(
                state -> {
                    int actual = increment(threadsAmount, state);
                    records.add(getRecord(state, doubleAccuracy, threadsAmount, expected, actual));
                });

        List<String> columns = List.of("Case", "Threads", "Expected", "Actual", "Lost");
        final TableUtils.Table table = new TableUtils.Table(columns, records);
        TableUtils.printResults(table);
    }

    private static TableUtils.Record getRecord(final State state,
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
                put("Case", simpleName(state));
            }}
        );
    }

    private static int increment(int threadsAmount,
                                 final State state
    )
    {
        Counter counter = new Counter();
        final List<Thread> threads = ConcurrencyUtils.createThreads(threadsAmount, (Integer id) -> new IncrementThread(counter, state));
        threads.forEach(Thread::start);
        ConcurrencyUtils.join(threads);
        return state.getI();
    }

    private static String getLossString(final double actual, double expected, int doubleAccuracy) {
        Double loss = 100 - actual / expected * 100;

        String lossStringTemplate = String.format("!.%sf", doubleAccuracy).replace("!", "%");
        return String.format(lossStringTemplate, loss);
    }

}
