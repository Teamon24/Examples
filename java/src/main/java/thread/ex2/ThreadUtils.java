package thread.ex2;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class ThreadUtils {
    public static <T> List<T> createThreads(final int amount, Function<Integer, T> create) {
        List<T> threads = new ArrayList<>();
        for (int i = 1; i <= amount; i++) {
            threads.add(create.apply(i));
        }
        return threads;
    }

    public static void sleep(final int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
