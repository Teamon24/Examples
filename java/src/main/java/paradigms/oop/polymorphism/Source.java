package paradigms.oop.polymorphism;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.util.Arrays;
import java.util.StringJoiner;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
class Source<T> {
    T value;

    public final void consume(T... values) {
        Arrays.stream(values).forEach(System.out::println);
    }

    public T produce() {
        return this.value;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Source.class.getSimpleName() + "[", "]")
            .add("value=" + value.getClass().getSimpleName())
            .toString();
    }

    public static class Instances {

        public static Source<Object> OBJECT = new Source<>(new Object());
        public static Source<I> I = new Source<>(new O());
        public static Source<O> O = new Source<>(new O());
        public static Source<A> A = new Source<>(new A());
        public static Source<B> B = new Source<>(new B());
        public static Source<C> C = new Source<>(new C());
        public static Source<D> D = new Source<>(new D());
    }
}
