package paradigms;

import java.util.function.BiFunction;
import java.util.function.Function;

public class FunctionalTricks {

    private static class A {}
    private static class B {}

    static class Curring {
        public static <A, B, C> Function<A, Function<B, C>> curry(final BiFunction<A, B, C> f) {
            return (A a) -> (B b) -> f.apply(a, b);
        }

        public static <A, B, C> BiFunction<A, B, C> uncurry(Function<A, Function<B, C>> f) {
            return (A a, B b) -> f.apply(a).apply(b);
        }
    }

    static class Closure {
        public static Function<Double, Double> linear(Integer a, Integer b) {
            return t -> t * a + b;
        }
    }

    public static void main(String[] args) {
        Function<A, Function<B, String>> curry = Curring.curry((A a, B b) -> a.toString() + b.toString());

        Function<B, String> applied = curry.apply(new A());
        applied.apply(new B());

        BiFunction<A, B, String> original = Curring.uncurry(curry);
        Function<Double, Double> linear = Closure.linear(3, 3);
    }

}
