package core.base.reference;

import core.base._misc.A;

import java.util.ArrayList;
import java.util.List;

public class Demo {
    public static void main(String[] args) {
        A a = new A(1, 1, 1, 1, new Object(), "String");
        A a1 = a;
        List<A> as = new ArrayList<>();
        as.add(a);

        System.out.println("a:           " + a);
        System.out.println("a1:          " + a1);
        System.out.println("as.get(0):   " + as.get(0));
        setI(a, 2);
        System.out.println();
        System.out.println("AFTER SET");
        System.out.println("a:           " + a);
        System.out.println("a1:          " + a1);
        System.out.println("as.get(0):   " + as.get(0));
    }

    static void setI(A a, final int i) {
        System.out.println("a in method: " + a);
        a.i = i;
    }
}
