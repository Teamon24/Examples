package core.base.equals_hash_code.equals.inheritence.transitivity_violation;

import core.base.equals_hash_code.equals.inheritence.X;
import core.base.equals_hash_code.equals.inheritence.ExampleUtils;

import java.util.Objects;

public class Z_Fixed extends Y_Fixed {
    String z;

    public Z_Fixed(Y_Fixed y, String z) {
        super(y);
        this.z = z;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o instanceof Z_Fixed) {
            Z_Fixed other = (Z_Fixed) o;
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
