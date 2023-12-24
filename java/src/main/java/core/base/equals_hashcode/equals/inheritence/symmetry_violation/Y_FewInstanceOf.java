package core.base.equals_hashcode.equals.inheritence.symmetry_violation;

import core.base.equals_hashcode.equals.inheritence.X;
import core.base.equals_hashcode.equals.inheritence.ExampleUtils;
import core.base.equals_hashcode.equals.inheritence.Y;
import lombok.Getter;

import java.util.Objects;

@Getter
public class Y_FewInstanceOf extends X {
    public String b2;

    public Y_FewInstanceOf(int x1, String x2, String b2) {
        super();
        this.x1 = x1;
        this.x2 = x2;
        this.b2 = b2;
    }

    public Y_FewInstanceOf(Y y) {
        super(y.x1, y.x2);
        this.b2 = y.b2;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (o instanceof Y_FewInstanceOf) {
            Y_FewInstanceOf other = (Y_FewInstanceOf) o;
            return super.equals(other) && Objects.equals(this.b2, other.b2);
        }

        if (o instanceof X) {
            X other = (X) o;
            return super.equals(other);
        }

        throw ExampleUtils.exceptionIfNoInstanceOf(this, o);
    }
}
