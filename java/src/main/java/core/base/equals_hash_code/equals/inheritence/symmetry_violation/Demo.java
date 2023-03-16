package core.base.equals_hash_code.equals.inheritence.symmetry_violation;

import core.base.equals_hash_code.equals.inheritence.A;
import core.base.equals_hash_code.equals.inheritence.B;
import utils.ClassUtils;

import static java.lang.String.*;
import static java.lang.System.out;

public class Demo {
    public static void main(String[] args) {
        A a = new A(42, "abc");
        B b = new B(a, "123456");
        checkSymmetry("Symmetry violation", a.equals(b), b.equals(a));


        A a2 = new A(a);
        B_CompositionFix voucher2 = new B_CompositionFix(b);
        String message2 = format("Composition fix (%s does not extend %s)", name(voucher2), name(a2));
        checkSymmetry(message2, a2.equals(voucher2), voucher2.equals(a2));


        A_ClassComparisonFix money3 = new A_ClassComparisonFix(a);
        B_ClassComparisonFix voucher3 = new B_ClassComparisonFix(b);
        String message3 = format("Strict class comparison fix (%s extends %s)", name(voucher3), name(money3));
        checkSymmetry(message3, money3.equals(voucher3), voucher3.equals(money3));


        A a4 = new A(a);
        B_FewInstanceOf voucher4 = new B_FewInstanceOf(b);
        String message4 = format("Ascending hierarchy checks fix (%s extends %s)", name(voucher3), name(a4));
        checkSymmetry(message4, a4.equals(voucher4), voucher4.equals(a4));

    }

    private static String name(Object o) {
        return ClassUtils.simpleName(o);
    }

    private static boolean checkSymmetry(final String title,
                                         final boolean moneyEqualsVoucher, final boolean voucherEqualsMoney)
    {
        String line = "-".repeat(title.length());
        out.println(line + "\n" + title + "\n" + line);
        out.println("a " + equalSymbol(moneyEqualsVoucher) + " b");
        out.println("b " + equalSymbol(voucherEqualsMoney) + " a");
        out.println();
        return moneyEqualsVoucher && voucherEqualsMoney;
    }

    private static String equalSymbol(boolean moneyEqualsVoucher) {
        return moneyEqualsVoucher ? "==" : "!=";
    }
}
