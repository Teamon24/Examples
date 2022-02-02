package core.base.equals_hash_code.equals.inheritence.transitivity_violation;

import core.base.equals_hash_code.equals.inheritence.A;
import core.base.equals_hash_code.equals.inheritence.symmetry_violation.ExampleUtils;

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

        if (o instanceof C_Fixed) {
            C_Fixed other = (C_Fixed) o;
            return super.equals(other) && Objects.equals(c1, other.c1);
        }

        if (o instanceof A) {
            A other = (A) o;
            return super.equals(other);
        }

        throw ExampleUtils.exceptionIfNoInstanceOfCase(this, o);
    }
}
