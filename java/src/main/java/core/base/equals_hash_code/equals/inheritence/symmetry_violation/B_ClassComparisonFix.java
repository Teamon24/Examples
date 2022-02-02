package core.base.equals_hash_code.equals.inheritence.symmetry_violation;

import core.base.equals_hash_code.equals.inheritence.B;
import lombok.Getter;

import java.util.Objects;

@Getter
public class B_ClassComparisonFix extends A_ClassComparisonFix {
    private String b1;

    public B_ClassComparisonFix(final B b) {
        super(b.a1, b.a2);
        this.b1 = b.getB2();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof B_ClassComparisonFix)) return false;
        B_ClassComparisonFix other = (B_ClassComparisonFix) o;
        return
            super.getA1() == other.getA1() &&
                Objects.equals(super.getA2(), other.getA2()) &&
                Objects.equals(this.b1, other.b1);
    }
}
