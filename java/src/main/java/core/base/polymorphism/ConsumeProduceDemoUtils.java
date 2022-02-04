package core.base.polymorphism;

import java.util.List;
import java.util.function.Function;

import static utils.RandomUtils.randomDescendant;

public final class ConsumeProduceDemoUtils {

    public static List<Function<? super A, ? extends O>> aos() {
        return List.of(
            (O o) -> randomDescendant(O.class, I.hierarchy, I::create),
            (A a) -> randomDescendant(A.class, I.hierarchy, I::create)
        );
    }

    public static List<Function<? super B, ? extends O>> bos() {
        return List.of(
            (O o) -> randomDescendant(O.class, I.hierarchy, I::create),
            (A a) -> randomDescendant(A.class, I.hierarchy, I::create),
            (B b) -> randomDescendant(B.class, I.hierarchy, I::create)
        );
    }

    public static List<Function<? super C, ? extends O>> cos() {
        return List.of(
            (O o) -> randomDescendant(O.class, I.hierarchy, I::create),
            (A a) -> randomDescendant(A.class, I.hierarchy, I::create),
            (B b) -> randomDescendant(B.class, I.hierarchy, I::create),
            (C b) -> randomDescendant(C.class, I.hierarchy, I::create)
        );
    }

    public static List<Function<? super D, ? extends O>> dos() {
        return List.of(
            (O o) -> randomDescendant(O.class, I.hierarchy, I::create),
            (A a) -> randomDescendant(A.class, I.hierarchy, I::create),
            (B b) -> randomDescendant(B.class, I.hierarchy, I::create),
            (C c) -> randomDescendant(C.class, I.hierarchy, I::create),
            (D d) -> randomDescendant(D.class, I.hierarchy, I::create)
        );
    }

}
