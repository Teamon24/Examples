package core.variance;

import core.collection.benchmark.utils.RandomUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Source<T> {
    T value;

    @SafeVarargs
    public final void consume(T... values) {
        Arrays.stream(values).forEach(System.out::println);
    }

    public T produce() {
        return this.value;
    }
}

public class Demo {

    public static void main(String[] args) {
        simpleExampleOfClassVariance();
        fullExampleOfVariance();
        fullExampleOfFunctionVariance();
    }

    public static void simpleExampleOfClassVariance() {
        //Covariance: produce => extends
        Source<? extends A> belowA = new Source<B>();
        Source<? extends B> belowB = new Source<C>();
        Source<? extends C> belowC = new Source<D>();

        A a = belowA.produce();
        B b = belowB.produce();
        C c = belowC.produce();

        //Contravariance: consume => super
        Source<? super A> aboveA = new Source<Object>();
        Source<? super B> aboveB = new Source<A>();
        Source<? super C> aboveC = new Source<B>();

        //Все, что выше А, может принимать все, что равно или ниже A: A,B,C
        aboveA.consume(new A(), new B(), new C());

        //Все, что выше B, может принимать все, что равно или ниже B: B,C
        aboveB.consume(new B(), new C());

        //Все, что выше C, может принимать все, что равно или ниже C: C
        aboveC.consume(new C());
    }

    public static void fullExampleOfVariance() {
        //Covariance: produce => extends
        List<Source<? extends A>> belowA = List.of(new Source<A>(), new Source<B>(), new Source<C>());
        List<Source<? extends B>> belowB = List.of(new Source<B>(), new Source<C>());
        List<Source<? extends C>> belowC = List.of(new Source<C>());

        //Всe, что производит типы ниже A, B, C может быть только под ссылкой A;
        belowA.forEach(source -> {
            A a = source.produce();
        });

        //Всe, что производит типы ниже B, C может быть только под ссылкой B;
        belowB.forEach(source -> {
            B b = source.produce();
        });

        //Всe, что производит типы ниже C, может быть только под ссылкой C;
        belowC.forEach(source -> {
            C c = source.produce();
        });

        //Contravariance: consume => super
        List<Source<? super A>> aboveA = List.of(new Source<A>(), new Source<Object>());
        List<Source<? super B>> aboveB = List.of(new Source<B>(), new Source<A>(), new Source<Object>());
        List<Source<? super C>> aboveC = List.of(new Source<C>(), new Source<B>(), new Source<A>(), new Source<Object>());

        //Все, что выше А, может принимать все, что равно или ниже A: A,B,C
        aboveA.forEach(source -> source.consume(new A(), new B(), new C()));

        //Все, что выше B, может принимать все, что равно или ниже B: B,C
        aboveB.forEach(source -> source.consume(new B(), new C()));

        //Все, что выше C, может принимать все, что равно или ниже C: C
        aboveC.forEach(source -> source.consume(new C()));
    }

    /**
     * Ковариантность функций по типу-параметру входного аргумента.
     * <p>Выражение
     * <pre>{@code
     *     Function<? extends In, ?> function = ()
     * }</pre>
     * означает, что ссылке может быть присвоена функция, тип входного аргумента которой может быть любым подтипом In.
     * <p>Тогда в runtime может произойти несоответствие:
     * <p>- объявленного типа входного аргумента
     * <p>- типа объекта, который передается как входной аргумент.
     * <p>Пример:
     * <pre>{@code
     * Function<? extends O, ?> accept
     * accept = (D d) -> new O();
     * accept = (C c) -> new O();
     * accept.apply(new A());
     * }</pre>
     * <p>Компилятор выдаст ошибку на строке accept.apply(new A()).
     * <p>D,C подтипы для типа O, но мы можем передать в функцию и объект типа A, т.к. это также подтип O.
     * Но A не приводится к типам D или С, т.к. A - супертип для D,C.
     */
    private static void fullExampleOfFunctionVariance() {
        List<Function<? super A, ? extends O>> aos = aos();
        List<Function<? super B, ? extends O>> bos = bos();
        List<Function<? super C, ? extends O>> cos = cos();
        List<Function<? super D, ? extends O>> dos = dos();

        List<? extends A> belowA = List.of(new A(), new B(), new C(), new D());
        List<? extends B> belowB = List.of(         new B(), new C(), new D());
        List<? extends C> belowC = List.of(                  new C(), new D());
        List<? extends D> belowD = List.of(                           new D());

        flat(belowA, belowB, belowC, belowD).forEach(subA ->
            flat(aos).forEach(ao -> ao.apply(subA)));

        flat(belowB, belowC, belowD).forEach(subB ->
            flat(aos, bos).forEach(bo -> bo.apply(subB)));

        flat(belowC, belowD).forEach(subC ->
            flat(aos, bos, cos).forEach(co -> co.apply(subC)));

        flat(belowD).forEach(subD ->
            flat(aos, bos, cos, dos).forEach(Do -> Do.apply(subD)));

    }

    private static List<Function<? super A, ? extends O>> aos() {
        List<Function<? super A, ? extends O>> aos = List.of(
            (O o) -> randomFrom(O.class, A.class, B.class, C.class, D.class),
            (A a) -> randomFrom(A.class, B.class, C.class, D.class)
        );
        return aos;
    }

    private static List<Function<? super B, ? extends O>> bos() {
        List<Function<? super B, ? extends O>> bos = List.of(
            (O o) -> randomFrom(O.class, A.class, B.class, C.class, D.class),
            (A a) -> randomFrom(A.class, B.class, C.class, D.class),
            (B b) -> randomFrom(B.class, C.class, D.class)
        );
        return bos;
    }

    private static List<Function<? super C, ? extends O>> cos() {
        return List.of(
            (O o) -> randomFrom(O.class, A.class, B.class, C.class, D.class),
            (A a) -> randomFrom(A.class, B.class, C.class, D.class),
            (B b) -> randomFrom(B.class, C.class, D.class),
            (C b) -> randomFrom(C.class, D.class)
        );
    }

    private static List<Function<? super D, ? extends O>> dos() {
        return List.of(
            (O o) -> randomFrom(O.class, A.class, B.class, C.class, D.class),
            (A a) -> randomFrom(A.class, B.class, C.class, D.class),
            (B b) -> randomFrom(B.class, C.class, D.class),
            (C c) -> randomFrom(C.class, D.class),
            (D d) -> randomFrom(D.class)
        );
    }

    private static <T> Stream<? extends T> flat(List<? extends T> ... belows) {
        return Stream.of(belows).flatMap(Collection::stream);
    }

    private static O random() {
        return randomFrom(A.class, B.class, C.class, D.class);
    }

    private static <T extends O> T randomFrom(Class<? extends T>... classes) {
        Class<?> randomClass = RandomUtils.randomFrom(Arrays.stream(classes).collect(Collectors.toList()));
        if (O.class.equals(randomClass)) return (T) new O();
        if (A.class.equals(randomClass)) return (T) new A();
        if (B.class.equals(randomClass)) return (T) new B();
        if (C.class.equals(randomClass)) return (T) new C();
        if (D.class.equals(randomClass)) return (T) new D();
        throw new RuntimeException("");
    }
}


