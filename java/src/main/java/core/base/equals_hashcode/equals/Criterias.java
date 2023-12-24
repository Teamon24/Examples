package core.base.equals_hashcode.equals;

import static java.lang.System.out;

/**
 * <p><strong>Критерии</strong>:
 * <ul>
 * <li><i><strong>рефлексивность</strong></i>: объект равен самому себе.</li>
 * <li><i><strong>симметричность</strong></i>: X.equals(Y) => Y.equals(X).</li>
 * <li><i><strong>транзитивность</strong></i>: X.equals(Y) & Y.equals(Z) => X.equals(Z).</li>
 * <li><i><strong>согласованность</strong></i>: если свойство (используемое в equals) объекта X меняется,
 * а у объета Y - нет, то X.equals(Y) должно изменить возвращаемое значение.</li>
 * <li><i><strong>неэквивалентность null</strong></i>: X != null => X.equals(null) == false.</li>
 * </ul>
 */
public class Criterias {
    public static void main(String[] args) {
        Object o = new Object();
        A a1 = createA(o);
        A a2 = createA(o);
        A a3 = createA(o);
        A same = a1;

        boolean reflectiveness = a1.equals(same);
        boolean symmetry = a1.equals(a2) && a2.equals(a1);
        boolean transitivity = a1.equals(a2) && a2.equals(a3) && a1.equals(a3);
        boolean consistency = consistency(a1, a2);
        boolean nonEquivalenceToNull = !a1.equals(null);

        out.println("1. Reflectiveness: "    + reflectiveness);
        out.println("2. Symmetry: "          + symmetry);
        out.println("3. Transitivity: "      + transitivity);
        out.println("4. Consistency: "       + consistency);
        out.println("5. Non-null criteria: " + nonEquivalenceToNull);
    }

    private static boolean consistency(A a1, A a2) {
        boolean beforeChanges = a1.equals(a2);
        a2.setP1(10);
        boolean afterChanges = !a1.equals(a2);
        return beforeChanges && afterChanges;
    }

    private static A createA(Object o) {
        return new A(1, 2, "3", o);
    }
}

