package core.base.polymorphism;

import java.util.List;

public class Hierarchy {}

interface I {
    void i ();

    List<Class<? extends I>> hierarchy = List.of(O.class, A.class, B.class, C.class, D.class);

    static <T extends I> T create(Class<?> randomClass) {
        if (I.class.equals(randomClass)) return (T) new O();
        if (O.class.equals(randomClass)) return (T) new O();
        if (A.class.equals(randomClass)) return (T) new A();
        if (B.class.equals(randomClass)) return (T) new B();
        if (C.class.equals(randomClass)) return (T) new C();
        if (D.class.equals(randomClass)) return (T) new D();
        throw new RuntimeException("There is no if-clause for class: " + randomClass.getSimpleName());
    }
}

class O implements I { @Override public void i() { } }
class A extends O {}
class B extends A {}
class C extends B {}
class D extends C {}

