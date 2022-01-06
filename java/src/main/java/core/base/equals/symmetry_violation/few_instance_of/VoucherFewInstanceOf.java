package core.base.equals.symmetry_violation.few_instance_of;

import core.base.equals.symmetry_violation.Money;
import core.base.equals.symmetry_violation.Voucher;
import lombok.Getter;

import java.util.Objects;

@Getter
public class VoucherFewInstanceOf extends Money {
    private String store;

    public VoucherFewInstanceOf(int amount, String currencyCode, String store) {
        super(amount, currencyCode);
        this.store = store;
    }

    public VoucherFewInstanceOf(final Voucher voucher) {
        super(voucher.getAmount(), voucher.getCurrencyCode());
        this.store = voucher.getStore();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;

        if (o instanceof VoucherFewInstanceOf other) {
            boolean amountEquals = super.getAmount() == other.getAmount();
            boolean currencyCodeEquals = Objects.equals(this.getCurrencyCode(), other.getCurrencyCode());
            boolean storeEquals = Objects.equals(this.store, other.store);
            return amountEquals && currencyCodeEquals && storeEquals;
        }

        if (o instanceof Money other) {
            boolean amountEquals = super.getAmount() == other.getAmount();
            boolean currencyCodeEquals = Objects.equals(this.getCurrencyCode(), other.getCurrencyCode());
            return amountEquals && currencyCodeEquals;
        }

        return false;
    }
}