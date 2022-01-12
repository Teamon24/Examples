package core.base.equals_hash_code.hashcode.inheritence;

public class A {
    private final int a1;
    private final String a2;
    public A(int a1, String a2) { this.a1 = a1; this.a2 = a2; }

    @Override
    public int hashCode() {
        int result = a1;
        result = 31 * result + (a2 != null ? a2.hashCode() : 0);
        return result;
    }

    public static void main(String[] args) {
        C c = new C(1, "a1", 2, "b2", "c1");
    }
}

class B extends A {
    private final int b1;
    private final String b2;
    public B(int a1, String a2, int b1, String b2) { super(a1, a2); this.b1 = b1; this.b2 = b2; }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + b1;
        result = 31 * result + (b2 != null ? b2.hashCode() : 0);
        return result;
    }
}

class C extends B {
    private final String c1;
    public C(int a1, String a2, int b1, String b2, String c1) { super(a1, a2, b1, b2); this.c1 = c1; }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (c1 != null ? c1.hashCode() : 0);
        return result;
    }
}
