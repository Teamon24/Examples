package core.base.reference;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Objects;

@Getter
@AllArgsConstructor
public class A { public int i; public double d; public float f; public long l; public Object object; public String string;
    public static boolean result = true;

    public A() {}

    public static boolean arePrimitivesEqual(final A a, A other) {
        return
            a.i == other.i &&
            a.d == other.d &&
            a.f == other.f &&
            a.l == other.l;
    }

    public A(final A a) {
        this.i = a.i;
        this.d = a.d;
        this.f = a.f;
        this.l = a.l;
        this.object = a.object;
        this.string = a.string;
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof A)) return false;

        A other = (A) o;

        boolean primitivesAreEqual = arePrimitivesEqual(this, other);
        if (!primitivesAreEqual) return false;

        boolean objectsAreEqual = Objects.equals(this.object, other.object);
        boolean stringsAreEqual = Objects.equals(this.string, other.string);
        return objectsAreEqual && stringsAreEqual;
    }

    @Override
    public int hashCode() {
        int result = 17;
        int prime = 31;
        return new HashCodeBuilder(prime, result)
            .append(i)
            .append(d)
            .append(f)
            .append(l)
            .append(object)
            .append(string).toHashCode();
    }

    @Override
    public String toString() {
        return "A{" +
            "i=" + i +
            ", d=" + d +
            ", f=" + f +
            ", l=" + l +
            ", object=" + object +
            ", string='" + string + '\'' +
            '}';
    }
}

