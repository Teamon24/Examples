package core.base.comparable;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Comparator;
import java.util.List;
import java.util.StringJoiner;

@Getter
@AllArgsConstructor
public class A implements Comparable<A>, Cloneable {

    private static final List<Comparator<A>> comparators =
        List.of(
            Comparator.comparing(A::getA),
            Comparator.comparing(A::getB),
            Comparator.comparing(A::getC).reversed()
        );

    public static final Comparator<A> COMPARATOR = (o1, o2) -> {
        int result = 0;
        for (Comparator<A> comparator : comparators) {
            int compared = comparator.compare(o1, o2);
            if (compared != 0) {
                return compared;
            }
        }
        return result;
    };

    Integer a;
    String b;
    C c;

    public static A of(Integer a, String b, C c) {
        return new A(a, b, c);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", A.class.getSimpleName() + "[", "]")
            .add("a=" + a)
            .add("b='" + b + "'")
            .add("c=" + c)
            .toString();
    }

    @Override
    public int compareTo(A a) {
        return COMPARATOR.compare(this, a);
    }

    @Override
    public A clone() {
        try {
            A clone = (A) super.clone();
            clone.a = this.a;
            clone.b = this.b;
            clone.c = (C) this.c.clone();
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
