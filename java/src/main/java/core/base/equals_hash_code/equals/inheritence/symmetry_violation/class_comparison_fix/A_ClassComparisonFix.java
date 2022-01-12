package core.base.equals_hash_code.equals.inheritence.symmetry_violation.class_comparison_fix;

import core.base.equals_hash_code.equals.inheritence.A;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@Getter
@AllArgsConstructor
public class A_ClassComparisonFix {
    private int a1;
    private String a2;

    public A_ClassComparisonFix(final A a) {
        this.a1 = a.a1;
        this.a2 = a.a2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (haveDifferentClass(this, o)) return false;
        A_ClassComparisonFix other = (A_ClassComparisonFix) o;
        return
            this.a1 == other.a1 &&
            Objects.equals(this.a2, other.a2);
    }

    public static<T> boolean haveDifferentClass(T t1, T t2) {
        return t2 == null || !t1.getClass().equals(t2.getClass());
    }
}

