package core.base.equals;

import lombok.Getter;
import lombok.Setter;

/**
 * <p><strong>Критерии</strong>:
 * <ul>
 * <li><i><strong>рефлексивность</strong></i>: объект равен самому себе.</li>
 * <li><i><strong>симметричность</strong></i>: X.equals(Y) => Y.equals(X).</li>
 * <li><i><strong>транзитивность</strong></i>: X.equals(Y) & Y.equals(Z) => X.equals(Z).</li>
 * <li><i><strong>согласованность</strong></i>: если свойство (используемое в equals) объекта X меняется,
 * а у объета Y - нет, то X.equals(Y) должно изменить возвращаемое значение.</li>
 * <li><i><strong>null-резистентность</strong></i>: X != null => X.equals(null) == false.</li>
 * </ul>
 */
public class Demo {
    public static void main(String[] args) {
        Object o = new Object();
        A a1 = new A(1, 2, "3", o);
        A same = a1;
        A a2 = new A(1, 2, "3", o);
        A a3 = new A(1, 2, "3", o);

        boolean reflectiveness = a1.equals(same);
        boolean symmetry = a1.equals(a2) && a2.equals(a1);
        boolean transitivity = a1.equals(a2) && a2.equals(a3) && a1.equals(a3);

        boolean a1EqualsA2BeforeSet = a1.equals(a2);
        a2.setPrim1(10);
        boolean consistency = a1EqualsA2BeforeSet && !a1.equals(a2);

        boolean nullResistivity = !a1.equals(null);

        System.out.println("1. Reflectiveness: " + reflectiveness);
        System.out.println("2. Symmetry: " + symmetry);
        System.out.println("3. Transitivity: " + transitivity);
        System.out.println("4. Consistency: " + consistency);
        System.out.println("5. NullResistivity: " + nullResistivity);
    }
}

@Getter
@Setter
class A {
    int prim1; int prim2; Object ref1; Object ref2;

    public A(int prim1, int prim2, Object ref1, Object ref2) {
        this.prim1 = prim1; this.prim2 = prim2; this.ref1 = ref1; this.ref2 = ref2;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) return true;
        if (!(other instanceof A a)) return false;

        boolean primitivesAreEqual = this.prim1 == a.prim2 && this.prim2 == a.prim2;
        if (!primitivesAreEqual) return false;

        boolean ref1AreEqual = this.ref1 == a.ref1 || this.ref1 != null && this.ref1.equals(a.ref2);
        boolean ref2AreEqual = this.ref1 == a.ref1 || this.ref1 != null && this.ref1.equals(a.ref2);

        return ref1AreEqual && ref2AreEqual;
    }
}

