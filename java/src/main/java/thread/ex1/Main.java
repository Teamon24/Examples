package thread.ex1;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        int threadsAmount = 100;
        final StateObject stateObject1 = new StateObjectImpl();
        final StateObject stateObject2 = new VolatileStateObject();
        final StateObject stateObject3 = new SynchronizedStateObject();

        Stream.of(
            stateObject1,
            stateObject2,
            stateObject3
        ).forEach(stateObject -> increment(threadsAmount, stateObject));
    }

    private static void increment(int threadsAmount,
                                  final StateObject stateObject) 
    {
        final List<Thread> threads = createThreads(threadsAmount, new Counter(), stateObject);
        threads.forEach(Thread::start);

        join(threads);

        int actual = stateObject.getI();
        int expected = Counter.LIMIT;

        Utils.printResults(actual, expected, stateObject, 2);
    }

    private static void join(final List<Thread> threads) {

        try {
            for (Thread thread : threads) {
                thread.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static List<Thread> createThreads(final int threadsAmount,
                                              final Counter checker,
                                              final StateObject stateObject)
    {
        ArrayList<Thread> threads = new ArrayList<>();
        for (int j = 0; j < threadsAmount; j++) {
            threads.add(new IncrementThread(checker, stateObject));
        }
        return threads;
    }

}
