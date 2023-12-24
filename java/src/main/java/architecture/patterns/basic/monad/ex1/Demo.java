package architecture.patterns.basic.monad.ex1;


import java.util.function.Function;

/**
 * <p>Any implementation of the monad must have two operations.
 *
 * <ul><li>The binding operation that allows us to transform a value
 * inside one monad into another monad.</li>
 * <pre>{@code <B> Monad<B> flatMap(function: Function<A, Monad<B>>) }</pre>
 * <li>And the return operation that allows us to get the monad from the value.</li>
 * <pre>{@code <A> Monad<A> apply(value: A) }</pre>
 *
 * <p>Also, these two operations must satisfy the following 3 laws of the monad,
 * where m, f, g are monads:
 * <ul>
 * <li><strong>left-identity law:</strong> m(x).flat(f) = f(x)</li>
 * <li><strong>right-identity law:</strong> m.flat(m)) = m</li>
 * <li><strong>associativity law:</strong> m.flat(f).flat(g) = m.flat(x -> f(x).flat(g)</li>
 * </ul>
 *
 */
public final class Demo {
    public static void main(String[] args) {
        Function<Integer, Try<Double>> f = (i) -> m(i * 2.0);
        Function<Double, Try<String>> g = (i) -> m(String.format("%s", i * 3.0));

        int value = 1;

        Try<Integer> m = m(value);

        System.out.printf("Left-identity law: %s%n",  m.flatMap(f).equals(f.apply(value)));
        System.out.printf("Right-identity law: %s%n", m.flatMap(x -> m(x)).equals(m));
        System.out.printf("Associativity law: %s%n",  m.flatMap(f).flatMap(g).equals(m.flatMap(i -> f.apply(i).flatMap(g))));

        try {
            Try.of(() -> 2)
                .flatMap((o) -> Try.of(() -> { System.out.println("Before a throw #1"); return "first"; }))
                .flatMap((o) -> Try.of((s) -> { System.out.println("Before a throw #2"); return o; }))
                .flatMap((o) -> Try.of(() -> { throw new RuntimeException("RTE"); }))
                .flatMap((o) -> Try.of(() -> { System.out.println("After a throw #3"); return o; }))
                .flatMap((o) -> Try.of(() -> { System.out.println("After a throw #4"); return o; }))
                .get();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private static <R> Try<R> m(R x) {
        return Try.of(() -> x);
    }
}

