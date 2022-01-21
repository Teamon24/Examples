package core.base.equals_hash_code.equals.inheritence.symmetry_violation.compostion_fix;

import core.base.equals_hash_code.equals.inheritence.A;
import core.base.equals_hash_code.equals.inheritence.B;
import core.base.equals_hash_code.equals.inheritence.symmetry_violation.class_comparison_fix.B_ClassComparisonFix;
import lombok.Getter;

import java.util.Objects;

@Getter
public class B_CompositionFix {

    private A a;
    private String b1;

    public B_CompositionFix(final B b) {
        this.a = new A(b.a1, b.a2);
        this.b1 = b.getB2();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof B_CompositionFix)) return false;
        B_CompositionFix other = (B_CompositionFix) o;
        return
            Objects.equals(this.a, other.a) &&
            Objects.equals(this.b1, other.b1);
    }
}
