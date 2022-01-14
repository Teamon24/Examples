package core.variance;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

class O {}
class A extends O {}
class B extends A {}
class C extends B {}
class D extends C {}

class Source<T> {
    T value;
    @SafeVarargs
    public final void consume(T... values) { Arrays.stream(values).forEach(System.out::println); }
    public T produce() { return this.value; }
}

public class Demo {
    public static void main(String[] args) {
        classVariance();
        functionVariance();
    }

    public static void classVariance() {
        //Covariance: produce => extends
        List<Source<? extends A>> belowA = List.of(new Source<A>(), new Source<B>(), new Source<C>());
        List<Source<? extends B>> belowB = List.of(                 new Source<B>(), new Source<C>());
        List<Source<? extends C>> belowC = List.of(                                  new Source<C>());

        //Всe, что производит типы ниже A, B, C может быть только под ссылкой A;
        belowA.forEach(source -> { A a = source.produce(); });

        //Всe, что производит типы ниже B, C может быть только под ссылкой B;
        belowB.forEach(source -> { B b = source.produce(); });

        //Всe, что производит типы ниже C, может быть только под ссылкой C;
        belowC.forEach(source -> { C c = source.produce(); });

        //Contravariance: consume => super
        List<Source<? super A>> aboveA = List.of(                                  new Source<A>(), new Source<Object>());
        List<Source<? super B>> aboveB = List.of(                 new Source<B>(), new Source<A>(), new Source<Object>());
        List<Source<? super C>> aboveC = List.of(new Source<C>(), new Source<B>(), new Source<A>(), new Source<Object>());

        //Все, что выше А, может принимать все, что равно или ниже A: A,B,C
        aboveA.forEach(source -> source.consume(new A(), new B(), new C()));

        //Все, что выше B, может принимать все, что равно или ниже B: B,C
        aboveB.forEach(source -> source.consume(         new B(), new C()));

        //Все, что выше C, может принимать все, что равно или ниже C: C
        aboveC.forEach(source -> source.consume(                  new C()));
    }

    private static void functionVariance() {
        Function<? super C, ? extends O> co = (O o) -> new A();
        Function<? super C, ? extends C> cc = (C a) -> new C();

        Function<? super B, ? extends A> ba = (O o) -> new A();
        Function<? super B, ? extends C> bc = (B b) -> new C();

        Function<? super A, ? extends A> aa = (O o) -> new A();
        Function<? super A, ? extends B> ab = (A a) -> new B();
        Function<? super A, ? extends C> ac = (A a) -> new C();
        Function<? super A, ? extends D> ad = (A a) -> new D();


        // Фунции ниже не будут принимать аргумент.
        // При использовании "Function<? extends T, ...>" мы говорим,
        // что ссылке может быть присвоена функция,
        // тип входного аргумента которой может быть любым подтипом T.
        // Тогда может случится несоответствие типов входного аргумента и объекта,
        // который передается как входной аргумент:
        // Function<? extends Number> soutNumber = (Double d) -> sout(d) или (Float f) -> sout(f);
        // soutNumber(new Integer(1));
        // Но Integer - не есть Double/Float
        Function<? extends A, ? super D> ad2 = (A a) -> new D();
        Function<? extends D, ? super A> da2 = (D d) -> new D();

        List<? extends A> belowA = List.of(new A(), new B(), new C(), new D());
        List<? extends C> belowC = List.of(new C(), new D());

        List<? super A> aboveA = List.of(new Object(), new A());
        List<? super C> aboveC = List.of(new Object(), new C());

        Stream.of(belowA, belowC).flatMap(Collection::stream).forEach(subA -> {
            A a = aa.apply(subA);
            C c = ac.apply(subA);
        });

        belowC.forEach(subC -> {
            A a = ba.apply(subC);
            C c = bc.apply(subC);
            O o = co.apply(subC);
            C c1 = cc.apply(subC);
        });

        belowA.forEach(subA -> {
            A a = aa.apply(subA);
            B b = ab.apply(subA);
            C c = ac.apply(subA);
            aboveA.add(a);
            aboveA.add(b);
            aboveA.add(c);
        });

        belowC.forEach(subC -> {
            C c = cc.apply(subC);
            D d = ad.apply(subC);
            aboveC.add(c);
            aboveC.add(d);
        });
    }
}


