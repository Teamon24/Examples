package core.base.equals_hash_code.equals;

import lombok.Getter;
import lombok.Setter;

/**
 *
 */
@Getter
@Setter
class A {
    int prim1;
    int prim2;
    Object ref1;
    Object ref2;

    public A(int prim1, int prim2, Object ref1, Object ref2) {
        this.prim1 = prim1;
        this.prim2 = prim2;
        this.ref1 = ref1;
        this.ref2 = ref2;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof A)) {
            return false;
        }

        A other = (A) o;

        return (this.prim1 == other.prim2 && this.prim2 == other.prim2) &&
            (this.ref1 == other.ref1 || this.ref1 != null && this.ref1.equals(other.ref1)) &&
            (this.ref2 == other.ref2 || this.ref2 != null && this.ref2.equals(other.ref2));
    }
}
