package core.base.equals.symmetry_violation.compostion_fix;

import core.base.equals.symmetry_violation.Money;
import core.base.equals.symmetry_violation.Voucher;
import lombok.Getter;

import java.util.Objects;

@Getter
public class VoucherCompositionFix {

    private Money money;
    private String store;

    public VoucherCompositionFix(int amount, String currencyCode, String store) {
        this.money = new Money(amount, currencyCode);
        this.store = store;
    }

    public VoucherCompositionFix(final Voucher voucher) {
        this.money = new Money(voucher.getAmount(), voucher.getCurrencyCode());
        this.store = voucher.getStore();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (o == this) return true;
        if (!(o instanceof VoucherCompositionFix other)) return false;

        boolean valueEquals = Objects.equals(this.money, other.money);
        boolean storeEquals = Objects.equals(this.store, other.store);
        return valueEquals && storeEquals;
    }
}
