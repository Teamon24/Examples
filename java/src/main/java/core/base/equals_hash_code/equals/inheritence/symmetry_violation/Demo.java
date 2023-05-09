package core.base.equals_hash_code.equals.inheritence.symmetry_violation;

import core.base.equals_hash_code.equals.inheritence.X;
import core.base.equals_hash_code.equals.inheritence.Y;
import utils.ClassUtils;

import static java.lang.String.*;
import static java.lang.System.out;

public class Demo {
    public static void main(String[] args) {
        X x = new X(42, "abc");
        Y y = new Y(x, "123456");
        symmetry("Symmetry violation", x, y);


        X x2 = new X(x);
        Y_CompositionFix y_compositionFix = new Y_CompositionFix(y);
        String message2 = compositionFixMessage(x2, y_compositionFix);
        symmetry(message2, x2, y_compositionFix);


        X_ComparisonFix x_comparisonFix = new X_ComparisonFix(x);
        Y_ComparisonFix y_comparisonFix = new Y_ComparisonFix(y);
        String message3 = strictClassComparisonFIxMessage(x_comparisonFix, y_comparisonFix);
        symmetry(message3, x_comparisonFix, y_comparisonFix);


        X x4 = new X(x);
        Y_FewInstanceOf y_fewInstanceOf = new Y_FewInstanceOf(y);
        String message4 = format("Ascending hierarchy checks fix (%s extends %s)", name(y_comparisonFix), name(x4));

        symmetry(message4, x4, y_fewInstanceOf);

    }

    private static String strictClassComparisonFIxMessage(X_ComparisonFix x_comparisonFix, Y_ComparisonFix y_comparisonFix) {
        String message3 = format("Strict class comparison fix (%s extends %s)", name(y_comparisonFix), name(x_comparisonFix));
        return message3;
    }

    private static String compositionFixMessage(X x2, Y_CompositionFix y_compositionFix) {
        String message2 = format("Composition fix (%s does not extend %s)", name(y_compositionFix), name(x2));
        return message2;
    }

    private static String name(Object o) {
        return ClassUtils.simpleName(o);
    }

    private static boolean symmetry(final String title,
                                    final Object o1,
                                    final Object o2)
    {
        String line = "-".repeat(title.length());
        out.println(line + "\n" + title + "\n" + line);
        boolean o1EqualsO2 = o1.equals(o2);
        boolean o2EqualsO2 = o2.equals(o1);
        out.println("a " + equalSymbol(o1EqualsO2) + " b");
        out.println("b " + equalSymbol(o2EqualsO2) + " a");
        out.println();
        return o1EqualsO2 && o2EqualsO2;
    }

    private static String equalSymbol(boolean moneyEqualsVoucher) {
        return moneyEqualsVoucher ? "==" : "!=";
    }
}
