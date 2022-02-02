package core.string;

import static utils.PrintUtils.printfln;

/**
 * Created by Артем on 15.12.2016.
 */
public class ImmutableStringExample {
    public static void main(String[] args) {
        int a = 3;
        String b = "3";
        doSomething(a, b);
        printfln("after doSomething: a = %d, b = %s", a, b);
        String newB = b;
        printfln("newB = b");
        printfln("before \"b\" changed: newB = %s", newB);
        b = "5";
        printfln("b = %s", b);
        printfln("after \"b\" changed: newB = %s", newB);
    }

    public static void doSomething(int a, String b) {
        printfln("in doSomething: a = %d, b = %s", a, b);
        a = 5;
        b = "5";
    }
}
