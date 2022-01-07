package core.base.equals.symmetry_violation.few_instance_of;

import core.base.equals.symmetry_violation.ExceptionUtils;
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
            return
                super.getAmount() == other.getAmount() &&
                Objects.equals(this.getCurrencyCode(), other.getCurrencyCode()) &&
                Objects.equals(this.store, other.store);
        }

        if (o instanceof Money other) {
            return
                super.getAmount() == other.getAmount() &&
                Objects.equals(this.getCurrencyCode(), other.getCurrencyCode());
        }

        throw ExceptionUtils.exceptionIfNoInstanceOfCheck(o, this);
    }
}