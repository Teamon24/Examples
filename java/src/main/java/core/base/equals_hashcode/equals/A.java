package core.base.equals_hashcode.equals;

import lombok.Getter;
import lombok.Setter;

/**
 *
 */
@Getter
@Setter
class A {
    int p1;
    int p2;
    Object r1;
    Object r2;

    public A(int p1, int p2, Object r1, Object r2) {
        this.p1 = p1;
        this.p2 = p2;
        this.r1 = r1;
        this.r2 = r2;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof A)) {
            return false;
        }

        A a = (A) o;

        return
            (p1 == a.p1 && p2 == a.p2) &&
            (r1 == a.r1 || r1 != null && r1.equals(a.r1)) &&
            (r2 == a.r2 || r2 != null && r2.equals(a.r2));
    }
}
