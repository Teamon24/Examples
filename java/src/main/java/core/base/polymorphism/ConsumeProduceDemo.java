package core.base.polymorphism;

import java.util.List;
import java.util.function.Function;

import static core.collection.benchmark.utils.StreamUtils.flat;

public class ConsumeProduceDemo {
    public static void main(String[] args) {
        simpleExampleOfClassVariance();
        fullExampleOfVariance();
        fullExampleOfFunctionVariance();
    }

    public static void simpleExampleOfClassVariance() {
        //Covariance: extends => ТОЛЬКО get
        Source<? extends A> belowA = Source.B;
        Source<? extends B> belowB = Source.C;
        Source<? extends C> belowC = Source.D;

        A a = belowA.get();
        B b = belowB.get();
        C c = belowC.get();

        //Contravariance: super => ТОЛЬКО add
        Source<? super A> aboveA = Source.O;
        Source<? super B> aboveB = Source.A;
        Source<? super C> aboveC = Source.B;

        //Все, что выше А, может принимать все, что равно или ниже A: A,B,C
        aboveA.add(new A(), new B(), new C());

        //Все, что выше B, может принимать все, что равно или ниже B: B,C
        aboveB.add(new B(), new C());

        //Все, что выше C, может принимать все, что равно или ниже C: C
        aboveC.add(new C());
    }

    public static void fullExampleOfVariance() {
        //Covariance: extends => ТОЛЬКО get
        List<Source<? extends A>> sourcesExtendA = List.of(Source.A, Source.B, Source.C);
        List<Source<? extends B>> sourcesExtendB = List.of(Source.B, Source.C);
        List<Source<? extends C>> sourcesExtendC = List.of(Source.C);

        //Всe, что производит типы ниже A, B, C может быть только под ссылкой A;
        sourcesExtendA.forEach(source -> {
            A a = source.get();
        });

        //Всe, что производит типы ниже B, C может быть только под ссылкой B;
        sourcesExtendB.forEach(source -> {
            B b = source.get();
        });

        //Всe, что производит типы ниже C, может быть только под ссылкой C;
        sourcesExtendC.forEach(source -> {
            C c = source.get();
        });

        //Contravariance: super => ТОЛЬКО add
        List<Source<? super A>> sourcesSuperA = List.of(Source.A, Source.O);
        List<Source<? super B>> sourcesSuperB = List.of(Source.B, Source.A, Source.O);
        List<Source<? super C>> sourcesSuperC = List.of(Source.C, Source.B, Source.A, Source.O);

        //Все, что выше А, может принимать все, что равно или ниже A: A,B,C
        sourcesSuperA.forEach(source -> source.add(new A(), new B(), new C() /*, new O() -- не скомпилируется */));

        //Все, что выше B, может принимать все, что равно или ниже B: B,C
        sourcesSuperB.forEach(source -> source.add(new B(), new C()));

        //Все, что выше C, может принимать все, что равно или ниже C: C
        sourcesSuperC.forEach(source -> source.add(new C()));
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
        List<Function<? super A, ? extends O>> aos = ConsumeProduceDemoUtils.aos();
        List<Function<? super B, ? extends O>> bos = ConsumeProduceDemoUtils.bos();
        List<Function<? super C, ? extends O>> cos = ConsumeProduceDemoUtils.cos();
        List<Function<? super D, ? extends O>> dos = ConsumeProduceDemoUtils.dos();

        List<? extends O> belowO = List.of(new O(), new A(), new B(), new C(), new D());
        List<? extends A> belowA = List.of(         new A(), new B(), new C(), new D());
        List<? extends B> belowB = List.of(                  new B(), new C(), new D());
        List<? extends C> belowC = List.of(                           new C(), new D());
        List<? extends D> belowD = List.of(                                    new D());

        flat(belowA, belowB, belowC, belowD).forEach(subA ->
            flat(aos).forEach(ao -> ao.apply(subA)));

        flat(belowB, belowC, belowD).forEach(subB ->
            flat(aos, bos).forEach(bo -> bo.apply(subB)));

        flat(belowC, belowD).forEach(subC ->
            flat(aos, bos, cos).forEach(co -> co.apply(subC)));

        flat(belowD).forEach(subD ->
            flat(aos, bos, cos, dos).forEach(Do -> Do.apply(subD)));

    }

}


