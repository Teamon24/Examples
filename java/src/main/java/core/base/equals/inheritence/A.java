package core.base.equals.inheritence;

import lombok.AllArgsConstructor;

import java.util.Objects;

@AllArgsConstructor
public class A {
    public int a1;
    public String a2;

    public A(final A a) {
        this.a1 = a.a1;
        this.a2 = a.a2;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof A other)) return false;
        return this.a1 == other.a1 && Objects.equals(this.a2, other.a2);
    }
}

