package core.base.equals_hash_code.equals.inheritence.transitivity_violation;


import core.base.equals_hash_code.equals.inheritence.X;
import core.base.equals_hash_code.equals.inheritence.Y;
import core.base.equals_hash_code.equals.inheritence.Z;
import utils.ClassUtils;

import static java.lang.String.format;

public class Demo {
    public static void main(String[] args) {
        X x = new X(1, "a2");
        Y y = new Y(x, "b2");
        Z z = new Z(y, "c1");

        Y_Fixed y_fixed = new Y_Fixed(x, "b2");
        Z_Fixed z_fixed = new Z_Fixed(y_fixed, "c1");

        symmetry(x, y);
        symmetry(y, z);
        symmetry(x, z);

        transitivity(x, y, z, "Transitivity was violated");
        transitivity(z, y, x, "Reversed transitivity was violated");


        symmetry(x,       y_fixed);
        symmetry(y_fixed, z_fixed);
        symmetry(x,       z_fixed);

        transitivity(x,       y_fixed, z_fixed, "Transitivity was violated");
        transitivity(z_fixed, y_fixed, x, "Reversed Transitivity was violated");
    }

    public static void symmetry(Object o1,
                                Object o2)
    {
        String message = format("Symmetry of: '%s' and '%s'", name(o1), name(o2));
        String line = "-".repeat(message.length());
        System.out.println(line + "\n" + message + "\n" + line);
        boolean symmetryCheck = equals(o1, o2) && equals(o2, o1);
        printIfViolated(symmetryCheck, "Symmetry was violated");
    }

    public static void transitivity(Object o1,
                                    Object o2,
                                    Object o3,
                                    String violationMessage)
    {
        String message = format("Transitivity of: '%s', '%s' and '%s'", name(o1), name(o2), name(o3));
        String line = "-".repeat(message.length());
        System.out.println(line + "\n" + message + "\n" + line);
        boolean transitivityCheck = equals(o1, o2) & equals(o2, o3) & equals(o1, o3);
        printIfViolated(transitivityCheck, violationMessage);
    }

    private static void printIfViolated(boolean isCorrect, String message) {
        if (!isCorrect) {
            String line2 = "!".repeat(message.length());
            System.out.println(line2 + "\n" + message + "\n" + line2 + "\n");
        }
    }

    private static boolean equals(Object o1, Object o2) {
        boolean equals = o1.equals(o2);
        String message = format("%s %s %s", name(o1), equals ? "==" : "!=", name(o2));
        System.out.println(message);
        return equals;
    }

    private static String name(Object o2) {
        return ClassUtils.simpleName(o2);
    }
}
