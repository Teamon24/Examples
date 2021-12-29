package string;

/**
 * Created by Артем on 15.12.2016.
 */
public class ImmutableStringExample {
    public static void main(String[] args) {
        int a = 3;
        String b = "3";
        doSomething(a, b);
        System.out.printf("after doSomething: a = %d, b = %s\n", a, b);
        String newB = b;
        System.out.printf("newB = b\n");
        System.out.printf("before \"b\" changed: newB = %s\n", newB);
        b = "5";
        System.out.printf("b = %s\n", b);
        System.out.printf("after \"b\" changed: newB = %s\n", newB);
    }

    public static void doSomething(int a, String b) {
        System.out.printf("in doSomething: a = %d, b = %s\n", a, b);
        a = 5;
        b = "5";
    }
}
