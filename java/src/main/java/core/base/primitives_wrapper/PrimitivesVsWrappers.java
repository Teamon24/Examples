package core.base.primitives_wrapper;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * <table style="width: 100%; overflow-wrap: break-word;">
 * <tbody>
 * <tr style="background-color: #588509;color: #ffffff">
 *     <td>A Primitive</td>
 *     <td>A Wrapper</td>
 * </tr>
 * <tr>
 *     <td>is a predefined data type provided by Java</td>
 *     <td>provides a mechanism to convert primitive type
 *     into object and vice versa</td>
 * </tr>
 *
 * <tr style="background-color: #202b0c;color: #ffffff">Associated Class</tr>
 * <tr>
 *     <td>is not an object so it does not belong to a class</td>
 *     <td>
 *         is used to create an object; therefore, it has a corresponding class
 *         <p><strong>Autoboxing</strong> (primitive -> wrapper) / <strong>Unboxing</strong> (wrapper -> primitive)
 *     </td>
 * </tr>
 *
 * <tr style="background-color: #202b0c;color: #ffffff">Null Values</tr>
 * <tr>
 *     <td>does not allow null values</td>
 *     <td>allows null values</td>
 * </tr>
 *
 * <tr style="background-color: #202b0c;color: #ffffff"> Memory Required </tr>
 * <tr>
 *     <td>required memory is lower</td>
 *     <td>required memory is higher.The Clustered Index does not require an additional space.</td>
 * </tr>
 *
 * <tr style="background-color: #202b0c;color: #ffffff">Collections</tr>
 * <tr>
 *     <td>is not used with collections</td>
 *     <td>can be used with a collections</td>
 * </tr>
 * <tr style="background-color: #202b0c;color: #ffffff">Misc</tr>
 * <tr>
 *     <td>even large scale calculations can be made faster</td>
 *     <td></td>
 * </tr>
 * <tr>
 *     <td></td>
 *     <td><strong>Cloning</strong>, <strong>Serialization</strong> work only with objects</td>
 * </tr>
 * <tr>
 *     <td></td>
 *     <td>On object data we can call multiple methods <strong>compareTo()</strong>, <strong>equals()</strong>, <strong>toString(</strong>)</td>
 * </tr>
 * </tbody>
 * </table>
 */
interface Difference {}

/**
 *  <pre>
 * {@code
 * |--------------|
 * | Stack memory |
 * |--------------|
 * |  77          | <-- int i = 77
 * |--------------|                               |--------------------------------|
 * |  B10         | <-- Integer a = 77 <--------> | Heap memory                    |
 * |--------------|                               |--------------------------------|
 *                                                | Object stored at address "B10" |
 *                                                |--------------------------------|
 * </pre>
 */
interface MemoryStored {}

interface ImmutabilityInJava {
    static void main(String[] args) {
        runInteger();
        runInt();
    }

    private static void runInteger() {
        Integer i = 1;
        System.out.println(i);
        modify(i);
        System.out.println(i);
    }

    private static void runInt() {
        int i = 2;
        System.out.println(i);
        modify(i);
        System.out.println(i);
    }

    private static void modify(Integer i) { i = i + 1; }
    private static void modify(int i) { i = i + 1; }
}

/**
 * Логика примера под вопросом....
 */
interface Performance {
    static void main(String[] args) throws IOException {
        for (int i = 0; i < 1000; i++) run(i);
    }

    private static void run(int i) {

        long no = time((ignored) -> new Custom());
        long yes = time((ignored) -> new Custom("boxingUnboxing"));

        System.out.println("------------------ " + i + " -------------------");
        System.out.println(yes > no ? "ok" : "error");
        System.out.println(
            "------------------ " +
            "-".repeat(Integer.valueOf(i).toString().length()) +
            " ------------------");
        System.out.println("no box/unbox: " + no);
        System.out.println("   box/unbox: " + yes);
        System.out.println();
    }

    private static long time(Consumer<Void> action) {
        var start = System.currentTimeMillis();
        action.accept(null);
        var finish = System.currentTimeMillis();
        return finish - start;
    }

    interface Snoop {}
    @AllArgsConstructor class SnoopInteger implements Snoop { @Getter final Integer id; }
    @AllArgsConstructor class SnoopInt implements Snoop { @Getter final int id; }

    class Custom {
        private Map<Integer, Snoop> map = new HashMap<>();
        private int anInt;

        public Custom(Object boxingUnboxing) {
            for (int i = 0; i < 700_000; i++) {
                map.put(i, new SnoopInteger(i));
            }
        }

        public Custom() {
            for (Integer i = 0; i < 700_000; i++) {
                map.put(i, new SnoopInteger(i));
            }
        }
    }
}
