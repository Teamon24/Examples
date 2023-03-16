package core.base.polymorphism;


import utils.RandomUtils;

import java.util.List;

public class PolymorphismDemo {
    public static void main(String[] args) {
        //1. Полиморфизм непарметризованных типов
        I i = randomDescendant(O.class);
        O o = randomDescendant(O.class);
        A a = randomDescendant(A.class);
        B b = randomDescendant(B.class);
        C c = randomDescendant(C.class);
        D d = randomDescendant(D.class);

        //2. Полиморфизм параметризованных типов

        //2.1. КОВАРИАНТНОСТЬ параметризованных типов
        //2.1.1. Параметризованные типы нековариантны

        /** HashSet<Number> integers != new HashSet<Integer>(); **/

        //2.1.2. Параметризованные типы ковариантны
        List<Source<? extends I>> subI = List.of(Source.D, Source.C, Source.B, Source.A, Source.O, Source.I);
        List<Source<? extends O>> subO = List.of(Source.D, Source.C, Source.B, Source.A, Source.O);
        List<Source<? extends A>> subA = List.of(Source.D, Source.C, Source.B, Source.A);
        List<Source<? extends B>> subB = List.of(Source.D, Source.C, Source.B);
        List<Source<? extends C>> subC = List.of(Source.D, Source.C);
        List<Source<? extends D>> subD = List.of(Source.D);

        Sources<? extends I> subISources = Sources.of(Source.D, Source.C, Source.B, Source.A, Source.O, Source.I);
        Sources<? extends O> subOSources = Sources.of(Source.D, Source.C, Source.B, Source.A, Source.O);
        Sources<? extends A> subASources = Sources.of(Source.D, Source.C, Source.B, Source.B);
        Sources<? extends B> subBSources = Sources.of(Source.D, Source.C, Source.B);
        Sources<? extends C> subCSources = Sources.of(Source.D, Source.C);
        Sources<? extends D> subDSources = Sources.of(Source.D);

        //2.2. КОНТРВАРИАНТНОСТЬ параметризованных типов

        //2.2.1. Параметризованные типы не контрвариантны
        /** HashSet<Integer> integers1 != new HashSet<Number>(); **/

        //2.1.2. Параметризованные типы контрвариантны
        List<Source<? super I>> superI = List.of(Source.OBJECT, Source.I);
        List<Source<? super O>> superO = List.of(Source.OBJECT, Source.I, Source.O);
        List<Source<? super A>> superA = List.of(Source.OBJECT, Source.I, Source.O, Source.A);
        List<Source<? super B>> superB = List.of(Source.OBJECT, Source.I, Source.O, Source.A, Source.B);
        List<Source<? super C>> superC = List.of(Source.OBJECT, Source.I, Source.O, Source.A, Source.B, Source.C);
        List<Source<? super D>> superD = List.of(Source.OBJECT, Source.I, Source.O, Source.A, Source.B, Source.C, Source.D);


        Sources<? super I> superISources = Sources.of(Source.OBJECT, Source.I);
        Sources<? super O> superOSources = Sources.of(Source.OBJECT, Source.I, Source.O);
        Sources<? super A> superASources = Sources.of(Source.OBJECT, Source.I, Source.O, Source.A);
        Sources<? super B> superBSources = Sources.of(Source.OBJECT, Source.I, Source.O, Source.A, Source.B);
        Sources<? super C> superCSources = Sources.of(Source.OBJECT, Source.I, Source.O, Source.A, Source.B, Source.C);
        Sources<? super D> superDSources = Sources.of(Source.OBJECT, Source.I, Source.O, Source.A, Source.B, Source.C, Source.D);
    }

    private static <T extends I> T randomDescendant(Class<? extends T> ascendantClass) {
        return RandomUtils.randomDescendant(ascendantClass, I.hierarchy, I::create);
    }
}


