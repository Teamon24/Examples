package inheritance.ex1;

/**
 * 11.01.2017.
 * _____________________________________________________________________________________________________________________________________________________________________________________________________________________________
 */
public class A {
    private void a(){}
    protected void aa(){}
    public void aaa(){}
}

class B extends A {
    void b() {
        super.aa();
    }
}

class C extends B {
    void c() {
        super.aa();
        super.b();
    }
}

class D extends C {
    void d() {
        super.aa();
        super.b();
        super.c();
    }
}
