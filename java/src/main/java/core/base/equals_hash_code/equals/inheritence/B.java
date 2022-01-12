package core.base.equals_hash_code.equals.inheritence;

import lombok.Getter;

import java.util.Objects;

@Getter
public class B extends A {
    public String b2;

    public B(final A a, final String b2) {
        super(a);
        this.b2 = b2;
    }

    public B(final B b) {
        super(b.a1, b.a2);
        this.b2 = b.b2;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof B other)) return false;

        return super.equals(other) && Objects.equals(this.b2, other.b2);
    }
}
