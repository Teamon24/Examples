package core.base.equals_hashcode.equals.inheritence;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Objects;

@NoArgsConstructor
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
        X x = (X) o;
        return this.x1 == x.x1 && Objects.equals(this.x2, x.x2);
    }
}

