package core.base.equals.symmetry_violation;

import core.base.equals.symmetry_violation.class_comparison_fix.MoneyClassComparisonFix;
import core.base.equals.symmetry_violation.compostion_fix.VoucherCompositionFix;
import core.base.equals.symmetry_violation.few_instance_of.VoucherFewInstanceOf;

public class Demo {
    public static void main(String[] args) {
        Money money = new Money(42, "USD");
        Voucher voucher = new Voucher(42, "USD", "Amazon");

        checkSymmetry("Symmetry violation",
            voucher.equals(money),
            money.equals(voucher));

        Money money2 = new Money(money);
        VoucherCompositionFix voucher2 = new VoucherCompositionFix(voucher);

        checkSymmetry("Composition fix (VoucherCompositionFix)", voucher2.equals(money2), money2.equals(voucher2));

        MoneyClassComparisonFix money3 = new MoneyClassComparisonFix(money);
        Voucher voucher3 = new Voucher(voucher);

        checkSymmetry("Class comparison fix (MoneyClassComparisonFix)",
            voucher3.equals(money3),
            money3.equals(voucher3));

        Money money4 = new Money(money);
        VoucherFewInstanceOf voucher4 = new VoucherFewInstanceOf(voucher);

        checkSymmetry("Ascending instance of checks fix (VoucherFewInstanceOf)",
            voucher4.equals(money4),
            money4.equals(voucher4));

    }

    private static void checkSymmetry(final String title,
                                      final boolean voucherEqualsMoney,
                                      final boolean moneyEqualsVoucher)
    {
        String line = "--------------------------------";
        System.out.println(line);
        System.out.println(title);
        System.out.println(line);
        System.out.printf("voucher != money = %s\n", !voucherEqualsMoney);
        System.out.printf("money != voucher = %s\n", !moneyEqualsVoucher);
        System.out.println();
    }
}
