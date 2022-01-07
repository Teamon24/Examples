package core.base.equals.symmetry_violation;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@Getter
@AllArgsConstructor
public class Money {
    private int amount;
    private String currencyCode;

    public Money(final Money money) {
        this.amount = money.getAmount();
        this.currencyCode = money.getCurrencyCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Money)) return false;
        Money other = (Money) o;
        return this.amount == other.amount &&
            Objects.equals(this.currencyCode, other.currencyCode);
    }
}

