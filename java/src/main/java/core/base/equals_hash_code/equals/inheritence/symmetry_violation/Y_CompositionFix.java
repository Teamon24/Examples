package core.base.equals_hash_code.equals.inheritence.symmetry_violation;

import core.base.equals_hash_code.equals.inheritence.X;
import core.base.equals_hash_code.equals.inheritence.Y;
import lombok.Getter;

import java.util.Objects;

@Getter
public class Y_CompositionFix {

    private X x;
    private String b1;

    public Y_CompositionFix(final Y y) {
        this.x = new X(y.x1, y.x2);
        this.b1 = y.getB2();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Y_CompositionFix)) return false;
        Y_CompositionFix other = (Y_CompositionFix) o;
        return
            Objects.equals(this.x, other.x) &&
            Objects.equals(this.b1, other.b1);
    }
}
