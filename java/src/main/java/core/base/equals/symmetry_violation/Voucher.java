package core.base.equals.symmetry_violation;

import lombok.Getter;

import java.util.Objects;

@Getter
public class Voucher extends Money {
    private String store;

    public Voucher(final int amount, final String currencyCode, final String store) {
        super(amount, currencyCode);
        this.store = store;
    }

    public Voucher(final Voucher voucher) {
        super(voucher.getAmount(), voucher.getCurrencyCode());
        this.store = voucher.store;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (o == this) return true;
        if (!(o instanceof Voucher other)) return false;

        boolean amountEquals = super.getAmount() == other.getAmount();
        boolean currencyCodeEquals = Objects.equals(this.getCurrencyCode(), other.getCurrencyCode());
        boolean storeEquals = Objects.equals(this.store, other.store);

        return amountEquals && currencyCodeEquals && storeEquals;
    }
}
