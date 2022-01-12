package core.base.equals_hash_code.equals.inheritence.symmetry_violation.few_instance_of;

import core.base.equals_hash_code.equals.inheritence.symmetry_violation.ExceptionUtils;
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

        if (o instanceof B_FewInstanceOf other) {
            return
                super.a1 == other.a1 &&
                Objects.equals(super.a2, other.a2) &&
                Objects.equals(this.store, other.store);
        }

        if (o instanceof A other) {
            return other.equals(this);
        }

        throw ExceptionUtils.exceptionIfNoInstanceOfCase(o, this);
    }
}