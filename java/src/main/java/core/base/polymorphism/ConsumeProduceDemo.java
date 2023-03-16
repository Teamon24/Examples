package core.base.polymorphism;

import utils.StreamUtils;

import java.util.List;
import java.util.function.Function;

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

        A a = belowA.produce();
        B b = belowB.produce();
        C c = belowC.produce();

        //Contravariance: super => ТОЛЬКО consume
        Source<? super A> aboveA = Source.O;
        Source<? super B> aboveB = Source.A;
        Source<? super C> aboveC = Source.B;

        //Все, что выше А, может принимать все, что равно или ниже A: A,B,C
        aboveA.consume(new A(), new B(), new C());

        //Все, что выше B, может принимать все, что равно или ниже B: B,C
        aboveB.consume(new B(), new C());

        //Все, что выше C, может принимать все, что равно или ниже C: C
        aboveC.consume(new C());
    }

    public static void fullExampleOfVariance() {
        //Covariance: extends => ТОЛЬКО get
        List<Source<? extends A>> sourcesExtendA = List.of(Source.A, Source.B, Source.C);
        List<Source<? extends B>> sourcesExtendB = List.of(Source.B, Source.C);
        List<Source<? extends C>> sourcesExtendC = List.of(Source.C);

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
        List<Source<? super A>> sourcesSuperA = List.of(Source.A, Source.O);
        List<Source<? super B>> sourcesSuperB = List.of(Source.B, Source.A, Source.O);
        List<Source<? super C>> sourcesSuperC = List.of(Source.C, Source.B, Source.A, Source.O);

        //Все, что выше А, может принимать все, что равно или ниже A: A,B,C
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

        StreamUtils.flat(belowO, belowA, belowB, belowC, belowD).forEach(subO ->
            StreamUtils.flat(oos).forEach(oo -> oo.apply(subO)));

        StreamUtils.flat(belowA, belowB, belowC, belowD).forEach(subA ->
            StreamUtils.flat(oos, aos).forEach(ao -> ao.apply(subA)));

        StreamUtils.flat(belowB, belowC, belowD).forEach(subB ->
            StreamUtils.flat(oos, aos, bos).forEach(bo -> bo.apply(subB)));

        StreamUtils.flat(belowC, belowD).forEach(subC ->
            StreamUtils.flat(oos, aos, bos, cos).forEach(co -> co.apply(subC)));

        StreamUtils.flat(belowD).forEach(subD ->
            StreamUtils.flat(oos, aos, bos, cos, dos).forEach(Do -> Do.apply(subD)));

    }

}


