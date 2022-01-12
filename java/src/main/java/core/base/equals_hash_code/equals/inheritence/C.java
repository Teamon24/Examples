package core.base.equals_hash_code.equals.inheritence;

import java.util.Objects;

public class C extends B {
    public String c1;

    public C(B b, String c1) {
        super(b);
        this.c1 = c1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof C)) return false;
        if (!super.equals(o)) return false;

        C c = (C) o;

        return Objects.equals(c1, c.c1);
    }
}
