package paradigms.oop.polymorphism;


import paradigms.oop.polymorphism.Source.Instances;
import utils.RandomUtils;

import java.util.List;

import static paradigms.oop.polymorphism.Source.Instances.D;
import static paradigms.oop.polymorphism.Source.Instances.C;
import static paradigms.oop.polymorphism.Source.Instances.B;
import static paradigms.oop.polymorphism.Source.Instances.A;
import static paradigms.oop.polymorphism.Source.Instances.O;
import static paradigms.oop.polymorphism.Source.Instances.OBJECT;

public class PolymorphismDemo {
    public static void main(String[] args) {
        //1. Полиморфизм не параметризованных типов
        I i = randomDescendant(O.class);
        O o = randomDescendant(O.class);
        A a = randomDescendant(A.class);
        B b = randomDescendant(B.class);
        C c = randomDescendant(C.class);
        D d = randomDescendant(D.class);

        //1.1. Динамический полиморфизм: переопределение метода
        d.method(1);

        //1.2. Статический полиморфизм: перегрузка метода
        d.method("1");

        //2. Полиморфизм параметризованных типов
        //2.1. Ковариантность параметризованных типов
        //2.1.1. Параметризованные типы нековариантны
        /** HashSet<Number> integers != new HashSet<Integer>(); **/

        //2.1.2. При использовании EXTENDS, типы - ковариантны
        Sources<? extends I> subI = Sources.of(D, C, B, A, O, Instances.I);
        Sources<? extends O> subO = Sources.of(D, C, B, A, O);
        Sources<? extends A> subA = Sources.of(D, C, B, B);
        Sources<? extends B> subB = Sources.of(D, C, B);
        Sources<? extends C> subC = Sources.of(D, C);
        Sources<? extends D> subD = Sources.of(D);

        List<Source<? extends I>> subIs = List.of(D, C, B, A, O, Instances.I);
        List<Source<? extends O>> subOs = List.of(D, C, B, A, O);
        List<Source<? extends A>> subAs = List.of(D, C, B, A);
        List<Source<? extends B>> subBs = List.of(D, C, B);
        List<Source<? extends C>> subCs = List.of(D, C);
        List<Source<? extends D>> subDs = List.of(D);

        //2.2. Контрвариантность параметризованных типов
        //2.2.1. Параметризованные типы неконтрвариантны
        /** HashSet<Integer> integers1 != new HashSet<Number>(); **/

        //2.1.2. При использовании SUPER, типы - контрвариантны
        List<Source<? super I>> superIs = List.of(OBJECT, Instances.I);
        List<Source<? super O>> superOs = List.of(OBJECT, Instances.I, O);
        List<Source<? super A>> superAs = List.of(OBJECT, Instances.I, O, A);
        List<Source<? super B>> superBs = List.of(OBJECT, Instances.I, O, A, B);
        List<Source<? super C>> superCs = List.of(OBJECT, Instances.I, O, A, B, C);
        List<Source<? super D>> superDs = List.of(OBJECT, Instances.I, O, A, B, C, D);

        Sources<? super I> superI = Sources.of(OBJECT, Instances.I);
        Sources<? super O> superO = Sources.of(OBJECT, Instances.I, O);
        Sources<? super A> superA = Sources.of(OBJECT, Instances.I, O, A);
        Sources<? super B> superB = Sources.of(OBJECT, Instances.I, O, A, B);
        Sources<? super C> superC = Sources.of(OBJECT, Instances.I, O, A, B, C);
        Sources<? super D> superD = Sources.of(OBJECT, Instances.I, O, A, B, C, D);
    }

    private static <T extends I> T randomDescendant(Class<? extends T> ascendantClass) {
        return RandomUtils.randomDescendant(ascendantClass, I.hierarchy, I::create);
    }
}


