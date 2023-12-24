package core.base.equals_hashcode.equals.inheritence.symmetry_violation;

import core.base.equals_hashcode.equals.inheritence.X;
import core.base.equals_hashcode.equals.inheritence.Y;

import static java.lang.String.format;
import static java.lang.System.out;
import static utils.ClassUtils.simpleName;

public class Demo {
    public static void main(String[] args) {
        X x = new X(42, "abc");
        Y y = new Y(x, "123456");
        symmetry(String.format("Symmetry violation when %s exntends %s", getName(y), getName(x)), x, y);

        X x2 = new X(x);
        Y_CompositionFix y_compositionFix = new Y_CompositionFix(y);
        String message2 = compositionFixMessage(x2, y_compositionFix);
        symmetry(message2, x2, y_compositionFix);

        // Объект родительского класса не равен объекту дочернего, т.к.
        // в родительском отсутствуют данные, которые есть в дочернем
        X_ComparisonFix x_comparisonFix = new X_ComparisonFix(x);
        Y_ComparisonFix y_comparisonFix = new Y_ComparisonFix(y);
        String message3 = strictClassComparisonFixMessage(x_comparisonFix, y_comparisonFix);
        symmetry(message3, x_comparisonFix, y_comparisonFix);

        // Объекты родительского и дочернего типа равны, если состояние родительского
        // равно состоянию унаследованному дочерним.
        // Сравнение не идет по уникальным свойствам дочернего класса.
        X x4 = new X(x);
        Y_FewInstanceOf y_fewInstanceOf = new Y_FewInstanceOf(y);
        String message4 = format("Ascending hierarchy checks fix (%s extends %s)", name(y_comparisonFix), name(x4));

        symmetry(message4, x4, y_fewInstanceOf);

    }

    private static String strictClassComparisonFixMessage(X_ComparisonFix x_comparisonFix, Y_ComparisonFix y_comparisonFix) {
        String message3 = format("Strict class comparison fix (%s extends %s)", name(y_comparisonFix), name(x_comparisonFix));
        return message3;
    }

    private static String compositionFixMessage(X x2, Y_CompositionFix y_compositionFix) {
        String message2 = format("Composition fix (%s does not extend %s)", name(y_compositionFix), name(x2));
        return message2;
    }

    private static String name(Object o) {
        return simpleName(o);
    }

    private static boolean symmetry(final String title,
                                    final Object o1,
                                    final Object o2)
    {
        String line = "-".repeat(title.length());
        out.println(line + "\n" + title + "\n" + line);
        boolean o1EqualsO2 = o1.equals(o2);
        boolean o2EqualsO2 = o2.equals(o1);
        out.println(getName(o1) + " " + equalSymbol(o1EqualsO2) + " " + getName(o2));
        out.println(getName(o2) + " " + equalSymbol(o2EqualsO2) + " " + getName(o1));
        out.println();
        return o1EqualsO2 && o2EqualsO2;
    }

    private static String getName(Object o1) {
        return simpleName(o1).split("_")[0];
    }

    private static String equalSymbol(boolean moneyEqualsVoucher) {
        return moneyEqualsVoucher ? "==" : "!=";
    }
}
