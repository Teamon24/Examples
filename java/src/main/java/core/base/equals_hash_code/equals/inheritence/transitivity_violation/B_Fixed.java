package core.base.equals_hash_code.equals.inheritence.transitivity_violation;

import core.base.equals_hash_code.equals.inheritence.A;
import core.base.equals_hash_code.equals.inheritence.symmetry_violation.ExceptionUtils;
import lombok.Getter;

import java.util.Objects;

@Getter
public class B_Fixed extends A {
    public String b2;

    public B_Fixed(final A a, final String b2) {
        super(a);
        this.b2 = b2;
    }

    public B_Fixed(final B_Fixed b) {
        super(b.a1, b.a2);
        this.b2 = b.b2;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (o instanceof B_Fixed b) {
            return super.equals(b) && Objects.equals(this.b2, b.b2);
        }

        if (o instanceof A a) {
            return super.equals(a);
        }

        throw ExceptionUtils.exceptionIfNoInstanceOfCase(this, o);
    }
}