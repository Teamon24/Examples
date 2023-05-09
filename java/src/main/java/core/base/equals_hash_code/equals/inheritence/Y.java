package core.base.equals_hash_code.equals.inheritence;

import lombok.Getter;

import java.util.Objects;

@Getter
public class Y extends X {
    public String b2;

    public Y(final X x, final String b2) {
        super(x);
        this.b2 = b2;
    }

    public Y(final Y y) {
        super(y.x1, y.x2);
        this.b2 = y.b2;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Y)) return false;
        Y other = (Y) o;
        return super.equals(o) && Objects.equals(this.b2, other.b2);
    }
}
