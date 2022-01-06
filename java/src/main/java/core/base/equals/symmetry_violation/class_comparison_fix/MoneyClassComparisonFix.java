package core.base.equals.symmetry_violation.class_comparison_fix;

import core.base.equals.symmetry_violation.Money;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@Getter
@AllArgsConstructor
public class MoneyClassComparisonFix {
    int amount;
    String currencyCode;

    public MoneyClassComparisonFix(final Money money) {
        this.amount = money.getAmount();
        this.currencyCode = money.getCurrencyCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (haveDifferentClass(this, o)) return false;
        MoneyClassComparisonFix other = (MoneyClassComparisonFix) o;
        boolean currencyCodeEquals = Objects.equals(this.currencyCode, other.currencyCode);
        return this.amount == other.amount && currencyCodeEquals;
    }

    public static<T> boolean haveDifferentClass(T t1, T t2) {
        return !t1.getClass().equals(t2.getClass());
    }
}

