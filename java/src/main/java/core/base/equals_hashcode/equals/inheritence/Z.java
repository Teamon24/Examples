package core.base.equals_hashcode.equals.inheritence;

import java.util.Objects;

public class Z extends Y {
    public String z;

    public Z(Y y, String z) {
        super(y);
        this.z = z;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Z)) return false;

        Z z = (Z) o;
        return super.equals(o) && Objects.equals(this.z, z.z);
    }
}
