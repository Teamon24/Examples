package core.base.equals;

import lombok.Getter;
import lombok.Setter;

/**
 *
 */
@Getter
@Setter
class My {
    int prim1;
    int prim2;
    Object ref1;
    Object ref2;

    public My(int prim1, int prim2, Object ref1, Object ref2) {
        this.prim1 = prim1;
        this.prim2 = prim2;
        this.ref1 = ref1;
        this.ref2 = ref2;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) return true;
        if (!(other instanceof My my)) return false;

        return (this.prim1 == my.prim2 && this.prim2 == my.prim2) &&
            (this.ref1 == my.ref1 || this.ref1 != null && this.ref1.equals(my.ref1)) &&
            (this.ref2 == my.ref2 || this.ref2 != null && this.ref2.equals(my.ref2));
    }
}
