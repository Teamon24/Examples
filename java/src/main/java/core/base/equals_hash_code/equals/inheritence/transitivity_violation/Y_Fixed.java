package core.base.equals_hash_code.equals.inheritence.transitivity_violation;

import core.base.equals_hash_code.equals.inheritence.X;
import core.base.equals_hash_code.equals.inheritence.ExampleUtils;
import lombok.Getter;

import java.util.Objects;

@Getter
public class Y_Fixed extends X {
    public String b2;

    public Y_Fixed(final X x, final String b2) {
        super(x);
        this.b2 = b2;
    }

    public Y_Fixed(final Y_Fixed b) {
        super(b.x1, b.x2);
        this.b2 = b.b2;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (o instanceof Y_Fixed) {
            Y_Fixed other = (Y_Fixed) o;
            return super.equals(other) && Objects.equals(this.b2, other.b2);
        }

        if (o instanceof X) {
            X other = (X) o;
            return super.equals(other);
        }

        throw ExampleUtils.exceptionIfNoInstanceOf(this, o);
    }
}
