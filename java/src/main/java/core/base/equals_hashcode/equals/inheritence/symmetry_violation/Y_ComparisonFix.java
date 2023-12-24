package core.base.equals_hashcode.equals.inheritence.symmetry_violation;

import core.base.equals_hashcode.equals.inheritence.Y;
import lombok.Getter;

import java.util.Objects;

@Getter
public class Y_ComparisonFix extends X_ComparisonFix {
    private String b1;

    public Y_ComparisonFix(final Y y) {
        super(y.x1, y.x2);
        this.b1 = y.getB2();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Y_ComparisonFix)) return false;
        Y_ComparisonFix other = (Y_ComparisonFix) o;
        return
            super.getA1() == other.getA1() &&
                Objects.equals(super.getA2(), other.getA2()) &&
                Objects.equals(this.b1, other.b1);
    }
}
