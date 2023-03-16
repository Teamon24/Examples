package core.base.equals_hash_code.hashcode;

import lombok.val;

import java.util.HashSet;
import java.util.Objects;

public class HashTables {
    public static void main(String[] args) {
        A a131 = new A(1, 3, 1);
        A a132 = new A(1, 3, 2);
        val hashSet = new HashSet<A>() {{
            add(new A(1, 2));
            add(new A(2, 1));
            add(a131);
            add(a132);
        }};

        System.out.println(hashSet.size());
    }
}

class A {
    int a;
    int b;
    int c;

    public A(int a, int b) {
        this.a = a;
        this.b = b;
    }

    public A(int a, int b, int c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof A)) return false;
        A a1 = (A) o;
        return a == a1.a && b == a1.b && c == a1.c;
    }

    @Override
    public int hashCode() {
        return Objects.hash(a, b);
    }
}
