package paradigms.oop.polymorphism;

import java.util.List;

interface I {
    void method(Integer i);
    void method(String i);

    default <T extends I> void sout(Class<T> iClass, Object i) {
        System.out.println(iClass.getSimpleName() + "#method("+i+")");
    }

    List<Class<? extends I>> hierarchy = List.of(O.class, A.class, B.class, C.class, D.class);

    static <T extends I> T create(Class<?> aClass) {
        if (I.class.equals(aClass)) return (T) new O();
        if (O.class.equals(aClass)) return (T) new O();
        if (A.class.equals(aClass)) return (T) new A();
        if (B.class.equals(aClass)) return (T) new B();
        if (C.class.equals(aClass)) return (T) new C();
        if (D.class.equals(aClass)) return (T) new D();
        throw new RuntimeException("There is no if-clause for class: " + aClass.getSimpleName());
    }
}

class O implements I {
    @Override public void method(Integer i) { sout(O.class, i); }
    @Override public void method(String i) { sout(O.class, i); }
}

class A extends O {}
class B extends A {}
class C extends B {}
class D extends C { @Override public void method(Integer i) { sout(D.class, i); } }

