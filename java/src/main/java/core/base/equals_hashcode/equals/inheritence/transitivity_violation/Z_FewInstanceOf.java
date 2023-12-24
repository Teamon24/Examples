package core.base.equals_hashcode.equals.inheritence.transitivity_violation;

import core.base.equals_hashcode.equals.inheritence.ExampleUtils;
import core.base.equals_hashcode.equals.inheritence.X;
import core.base.equals_hashcode.equals.inheritence.Z;
import core.base.equals_hashcode.equals.inheritence.symmetry_violation.Y_FewInstanceOf;

import java.util.Objects;

public class Z_FewInstanceOf extends Y_FewInstanceOf {
    String z;

    public Z_FewInstanceOf(Z z) {
        super(z.x1, z.x2, z.b2);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o instanceof Z_FewInstanceOf) {
            Z_FewInstanceOf other = (Z_FewInstanceOf) o;
            return super.equals(other) && Objects.equals(z, other.z);
        }

        if (o instanceof X) {
            X other = (X) o;
            return super.equals(other);
        }

        throw ExampleUtils.exceptionIfNoInstanceOf(this, o);
    }

    @Override
    public String toString() {
        return "{\"c1\" : " + (z == null ? null : z) + "}";
    }
}
