package core.base.equals.inheritence.transitivity_violation;

import core.base.equals.inheritence.A;
import core.base.equals.inheritence.symmetry_violation.ExceptionUtils;

import java.util.Objects;

public class C_Fixed extends B_Fixed {
    String c1;

    public C_Fixed(B_Fixed b, String c1) {
        super(b);
        this.c1 = c1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o instanceof C_Fixed c) {
            return super.equals(c) && Objects.equals(c1, c.c1);
        }

        if (o instanceof A lowerThanC) {
            return super.equals(lowerThanC);
        }

        throw ExceptionUtils.exceptionIfNoInstanceOfCase(this, o);
    }
}
