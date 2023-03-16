package core.base.passing_args;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.function.Consumer;

import static utils.PrintUtils.printfln;
import static java.lang.System.out;

@NoArgsConstructor
@AllArgsConstructor
class Source {
    int value;
}

class Driver {

    protected static final int NEW_VALUE = 50;
    protected static final int VALUE = 10;

    public static void main(String[] args) {
        test(new Source(VALUE), bitTest -> updateObject(bitTest, "UpdateObject"));
        test(new Source(VALUE), bitTest -> replaceObject(bitTest, "ReplaceObject"));
    }

    private static void test(Source original, Consumer<Source> setter) {
        int valueBeforeReplacing = original.value;
        String title = "Reference before passing: ";
        String refWithClassName = refNumber(original);
        printlnLine(title.length() + refWithClassName.length());
        out.println(title + refWithClassName);
        setter.accept(original);
        printfln("Before setting a new value: %s", valueBeforeReplacing);
        printfln("After setting a new value: %s", original.value);
    }

    public static void replaceObject(Source copy, String methodName) {
        out.println("\"" + methodName + "\": reference after passing - " + refNumber(copy));
        out.println("\"" + methodName + "\": changing reference's object");
        copy = new Source();
        out.println("\"" + methodName + "\": set a new value - " + NEW_VALUE);
        copy.value = NEW_VALUE;
    }

    public static void updateObject(Source copy, String methodName) {
        out.println("\"" + methodName + "\": reference after passing - " + refNumber(copy));
        copy.value = NEW_VALUE;
    }

    private static void printlnLine(int length) {
        out.println("-".repeat(length));
    }

    private static String refNumber(Source copy) {
        return copy.toString().split("@")[1];
    }
}