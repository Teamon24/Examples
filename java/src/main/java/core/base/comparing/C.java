package core.base.comparing;

import lombok.AllArgsConstructor;

import java.util.StringJoiner;

@AllArgsConstructor
public
class C implements Comparable<C>, Cloneable {

    Integer c;
    String d;

    @Override
    public int compareTo(C o) {
        int i = o.c.compareTo(this.c);
        if (i == 0) return o.d.compareTo(this.d); else return i;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", C.class.getSimpleName() + "[", "]")
            .add("c=" + c)
            .add("d='" + d + "'")
            .toString();
    }

    public static C of(Integer c, String d) {
        return new C(c, d);
    }

    @Override
    public C clone() {
        try {
            C clone = (C) super.clone();
            clone.c = this.c;
            clone.d = this.d;
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
