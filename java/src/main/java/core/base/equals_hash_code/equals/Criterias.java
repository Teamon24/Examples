package core.base.equals_hash_code.equals;

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
        My a1 = new My(1, 2, "3", o);
        My same = a1;
        My a2 = new My(1, 2, "3", o);
        My a3 = new My(1, 2, "3", o);

        boolean reflectiveness = a1.equals(same);
        boolean symmetry = a1.equals(a2) && a2.equals(a1);
        boolean transitivity = a1.equals(a2) && a2.equals(a3) && a1.equals(a3);

        boolean a1EqualsA2BeforeSet = a1.equals(a2);
        a2.setPrim1(10);
        boolean consistency = a1EqualsA2BeforeSet && !a1.equals(a2);

        boolean nullInequivalence = !a1.equals(null);

        System.out.println("1. Reflectiveness: " + reflectiveness);
        System.out.println("2. Symmetry: " + symmetry);
        System.out.println("3. Transitivity: " + transitivity);
        System.out.println("4. Consistency: " + consistency);
        System.out.println("5. Null-Inequivalence: " + nullInequivalence);
    }
}

