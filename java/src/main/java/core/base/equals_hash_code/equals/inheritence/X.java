package core.base.equals_hash_code.equals.inheritence;

import lombok.AllArgsConstructor;

import java.util.Objects;

@AllArgsConstructor
public class X {
    public int x1;
    public String x2;

    public X(final X x) {
        this.x1 = x.x1;
        this.x2 = x.x2;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof X)) return false;
        X other = (X) o;
        return this.x1 == other.x1 && Objects.equals(this.x2, other.x2);
    }
}

