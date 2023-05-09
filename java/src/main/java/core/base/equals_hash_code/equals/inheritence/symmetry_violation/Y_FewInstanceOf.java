package core.base.equals_hash_code.equals.inheritence.symmetry_violation;

import core.base.equals_hash_code.equals.inheritence.X;
import core.base.equals_hash_code.equals.inheritence.Y;
import core.base.equals_hash_code.equals.inheritence.ExampleUtils;
import lombok.Getter;

import java.util.Objects;

@Getter
public class Y_FewInstanceOf extends X {
    private String string;

    public Y_FewInstanceOf(final Y y) {
        super(y.x1, y.x2);
        this.string = y.getB2();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;

        if (o instanceof Y_FewInstanceOf) {
            Y_FewInstanceOf other = (Y_FewInstanceOf) o;
            return
                super.x1 == other.x1 &&
                Objects.equals(super.x2, other.x2) &&
                Objects.equals(this.string, other.string);
        }

        if (o instanceof X) {
            X other = (X) o;
            return super.equals(other);
        }

        throw ExampleUtils.exceptionIfNoInstanceOf(this, o);
    }
}