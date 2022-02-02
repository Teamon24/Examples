package core.base.equals_hash_code.equals.inheritence.symmetry_violation;

import core.base.equals_hash_code.equals.inheritence.A;
import core.base.equals_hash_code.equals.inheritence.B;
import lombok.Getter;

import java.util.Objects;

@Getter
public class B_FewInstanceOf extends A {
    private String store;

    public B_FewInstanceOf(final B b) {
        super(b.a1, b.a2);
        this.store = b.getB2();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;

        if (o instanceof B_FewInstanceOf) {
            B_FewInstanceOf other = (B_FewInstanceOf) o;
            return
                super.a1 == other.a1 &&
                Objects.equals(super.a2, other.a2) &&
                Objects.equals(this.store, other.store);
        }

        if (o instanceof A) {
            A other = (A) o;
            return other.equals(this);
        }

        throw ExampleUtils.exceptionIfNoInstanceOfCase(o, this);
    }
}