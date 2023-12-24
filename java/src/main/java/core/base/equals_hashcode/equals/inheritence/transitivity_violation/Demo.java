package core.base.equals_hashcode.equals.inheritence.transitivity_violation;


import core.base.equals_hashcode.equals.inheritence.X;
import core.base.equals_hashcode.equals.inheritence.Y;
import core.base.equals_hashcode.equals.inheritence.Z;
import core.base.equals_hashcode.equals.inheritence.symmetry_violation.Y_FewInstanceOf;
import org.apache.commons.lang3.StringUtils;
import utils.ClassUtils;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.String.format;

public class Demo {
    public static void main(String[] args) {
        X x = new X(1, "a2");
        Y y = new Y(x, "b2");
        Z z = new Z(y, "c1");

        Y_FewInstanceOf y_fio = new Y_FewInstanceOf(y);
        Z_FewInstanceOf z_fio = new Z_FewInstanceOf(z);

        symmetry(x, y);
        symmetry(y, z);
        symmetry(x, z);

        symmetry(x, y_fio);
        symmetry(y_fio, z_fio);
        symmetry(x, z_fio);

        sout(5);

        transitivity(x, y, z, "Transitivity was violated");
        transitivity(z, y, x, "Reversed transitivity was violated");

        transitivity(x, y_fio, z_fio, "Transitivity was violated");
        transitivity(z_fio, y_fio, x, "Reversed Transitivity was violated");
    }

    private static void sout(int i) {
        for (int j = 0; j < i; j++) {
            System.out.println();
        }
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
            System.out.println(message);
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
