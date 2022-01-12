package core.base.equals.inheritence.transitivity_violation;


import core.base.equals.inheritence.A;
import core.base.equals.inheritence.B;
import core.base.equals.inheritence.C;

public class Demo {
    public static void main(String[] args) {
        A a = new A(1, "a2");
        B b = new B(a, "b2");
        C c = new C(b, "c1");

        B_Fixed bFixed = new B_Fixed(a, "b2");
        C_Fixed cFixed = new C_Fixed(bFixed, "c1");

        symmetry(a, b);
        symmetry(b, c);
        symmetry(a, c);

        transitivity(a, b, c, "Transitivity was violated");
        transitivity(c, b, a, "Reversed transitivity was violated");


        symmetry(a, bFixed);
        symmetry(bFixed, cFixed);
        symmetry(a, cFixed);

        transitivity(a, bFixed, cFixed, "Transitivity was violated");
        transitivity(cFixed, bFixed, a, "Reversed Transitivity was violated");
    }

    public static void symmetry(Object o1, Object o2) {
        String message = "Symmetry of: '%s' and '%s'".formatted(name(o1), name(o2));
        String line = "-".repeat(message.length());
        System.out.println(line + "\n" + message + "\n" + line);
        printIfViolated(
            equals(o1, o2) &&
                equals(o2, o1),
            "Symmetry was violated"
        );
    }

    public static void transitivity(Object o1, Object o2, Object o3, String transitivityViolationMessage) {
        String message = "Transitivity of: '%s', '%s' and '%s'".formatted(name(o1), name(o2), name(o3));
        String line = "-".repeat(message.length());
        System.out.println(line + "\n" + message + "\n" + line);
        printIfViolated(
            equals(o1, o2) &
                equals(o2, o3) &
                equals(o1, o3),
            transitivityViolationMessage);
    }

    private static void printIfViolated(boolean isCorrect, String message) {
        if (!isCorrect) {
            String line2 = "!".repeat(message.length());
            System.out.println(line2 + "\n" + message + "\n" + line2 + "\n");
        }
    }

    private static boolean equals(Object o1, Object o2) {
        boolean equals = o1.equals(o2);
        String message = "%s %s %s".formatted(name(o1), equals ? "==" : "!=", name(o2));
        System.out.println(message);
        return equals;
    }

    private static String name(Object o2) {
        return o2.getClass().getSimpleName();
    }
}
