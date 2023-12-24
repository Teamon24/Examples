package paradigms.oop.polymorphism;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import static paradigms.oop.polymorphism.Source.*;

public class ConsumeProduceDemo {
    public static void main(String[] args) {
        simpleExampleOfClassVariance();
        fullExampleOfVariance();
        fullExampleOfFunctionVariance();
    }

    public static void simpleExampleOfClassVariance() {
        //Covariance: extends => ТОЛЬКО get
        Source<? extends A> belowA = Instances.B;
        belowA = Instances.C;
        belowA = Instances.D;

        Source<? extends B> belowB = Instances.C;
        belowB = Instances.D;

        Source<? extends C> belowC = Instances.D;

        A a = belowA.produce();
        B b = belowB.produce();
        C c = belowC.produce();

        //Contravariance: super => ТОЛЬКО consume
        Source<? super A> aboveA = Instances.O;
        Source<? super B> aboveB = Instances.A;
        Source<? super C> aboveC = Instances.B;

        //Все, что выше А, может принимать все, что равно или ниже A: A,B,C
        aboveA.consume(new A(), new B(), new C());

        //Все, что выше B, может принимать все, что равно или ниже B: B,C
        aboveB.consume(new B(), new C());

        //Все, что выше C, может принимать все, что равно или ниже C: C
        aboveC.consume(new C());
    }

    public static void fullExampleOfVariance() {
        //Covariance: extends => ТОЛЬКО get
        List<Source<? extends A>> sourcesExtendA = List.of(Instances.A, Instances.B, Instances.C);
        List<Source<? extends B>> sourcesExtendB = List.of(Instances.B, Instances.C);
        List<Source<? extends C>> sourcesExtendC = List.of(Instances.C);

        //Всe, что производит типы ниже A, B, C может быть только под ссылкой A;
        sourcesExtendA.forEach(source -> {
            A a = source.produce();
        });

        //Всe, что производит типы ниже B, C может быть только под ссылкой B;
        sourcesExtendB.forEach(source -> {
            B b = source.produce();
        });

        //Всe, что производит типы ниже C, может быть только под ссылкой C;
        sourcesExtendC.forEach(source -> {
            C c = source.produce();
        });

        //Contravariance: super => ТОЛЬКО consume
        List<Source<? super A>> sourcesSuperA = List.of(Instances.A, Instances.O);
        List<Source<? super B>> sourcesSuperB = List.of(Instances.B, Instances.A, Instances.O);
        List<Source<? super C>> sourcesSuperC = List.of(Instances.C, Instances.B, Instances.A, Instances.O);

        //Все, что выше А, может принимать все, что равно или ниже A: A, B, C
        sourcesSuperA.forEach(source -> source.consume(new A(), new B(), new C() /*, new O() -- не скомпилируется */));

        //Все, что выше B, может принимать все, что равно или ниже B: B,C
        sourcesSuperB.forEach(source -> source.consume(new B(), new C()));

        //Все, что выше C, может принимать все, что равно или ниже C: C
        sourcesSuperC.forEach(source -> source.consume(new C()));
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
        List<Function<? super O, ? extends O>> oos = ConsumeProduceDemoUtils.oos();
        List<Function<? super A, ? extends O>> aos = ConsumeProduceDemoUtils.aos();
        List<Function<? super B, ? extends O>> bos = ConsumeProduceDemoUtils.bos();
        List<Function<? super C, ? extends O>> cos = ConsumeProduceDemoUtils.cos();
        List<Function<? super D, ? extends O>> dos = ConsumeProduceDemoUtils.dos();

        List<? extends O> belowO = List.of(new O(), new A(), new B(), new C(), new D());
        List<? extends A> belowA = List.of(         new A(), new B(), new C(), new D());
        List<? extends B> belowB = List.of(                  new B(), new C(), new D());
        List<? extends C> belowC = List.of(                           new C(), new D());
        List<? extends D> belowD = List.of(                                    new D());

        flat(belowO, belowA, belowB, belowC, belowD).forEach(subO ->
            flat(oos).forEach(oo -> oo.apply(subO)));

        flat(belowA, belowB, belowC, belowD).forEach(subA ->
            flat(oos, aos).forEach(ao -> ao.apply(subA)));

        flat(belowB, belowC, belowD).forEach(subB ->
            flat(oos, aos, bos).forEach(bo -> bo.apply(subB)));

        flat(belowC, belowD).forEach(subC ->
            flat(oos, aos, bos, cos).forEach(co -> co.apply(subC)));

        flat(belowD).forEach(subD ->
            flat(oos, aos, bos, cos, dos).forEach(Do -> Do.apply(subD)));
    }

    public static <T> Stream<? extends T> flat(List<? extends T> ... belows) {
        return Stream.of(belows).flatMap(Collection::stream);
    }
}


