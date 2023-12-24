package paradigms.oop.polymorphism;

import utils.RandomUtils;

import java.util.List;
import java.util.function.Function;

public final class ConsumeProduceDemoUtils {

    public static List<Function<? super O, ? extends O>> oos() {
        return List.of(
            (O o) -> RandomUtils.randomDescendant(O.class, I.hierarchy, I::create)
        );
    }

    public static List<Function<? super A, ? extends O>> aos() {
        return List.of(
            (O o) -> RandomUtils.randomDescendant(O.class, I.hierarchy, I::create),
            (A a) -> RandomUtils.randomDescendant(A.class, I.hierarchy, I::create)
        );
    }

    public static List<Function<? super B, ? extends O>> bos() {
        return List.of(
            (O o) -> RandomUtils.randomDescendant(O.class, I.hierarchy, I::create),
            (A a) -> RandomUtils.randomDescendant(A.class, I.hierarchy, I::create),
            (B b) -> RandomUtils.randomDescendant(B.class, I.hierarchy, I::create)
        );
    }

    public static List<Function<? super C, ? extends O>> cos() {
        return List.of(
            (O o) -> RandomUtils.randomDescendant(O.class, I.hierarchy, I::create),
            (A a) -> RandomUtils.randomDescendant(A.class, I.hierarchy, I::create),
            (B b) -> RandomUtils.randomDescendant(B.class, I.hierarchy, I::create),
            (C b) -> RandomUtils.randomDescendant(C.class, I.hierarchy, I::create)
        );
    }

    public static List<Function<? super D, ? extends O>> dos() {
        return List.of(
            (O o) -> RandomUtils.randomDescendant(O.class, I.hierarchy, I::create),
            (A a) -> RandomUtils.randomDescendant(A.class, I.hierarchy, I::create),
            (B b) -> RandomUtils.randomDescendant(B.class, I.hierarchy, I::create),
            (C c) -> RandomUtils.randomDescendant(C.class, I.hierarchy, I::create),
            (D d) -> RandomUtils.randomDescendant(D.class, I.hierarchy, I::create)
        );
    }

}
