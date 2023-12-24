package core.base.equals_hashcode.equals.inheritence.symmetry_violation;

import core.base.equals_hashcode.ExampleUtils;
import core.base.equals_hashcode.equals.inheritence.X;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@Getter
@AllArgsConstructor
public class X_ComparisonFix {
    private int a1;
    private String a2;

    public X_ComparisonFix(final X x) {
        this.a1 = x.x1;
        this.a2 = x.x2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (ExampleUtils.haveDifferentClass(this, o)) return false;
        X_ComparisonFix other = (X_ComparisonFix) o;
        return
            this.a1 == other.a1 &&
            Objects.equals(this.a2, other.a2);
    }
}

