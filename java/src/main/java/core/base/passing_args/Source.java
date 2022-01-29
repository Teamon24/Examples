package core.base.passing_args;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.function.Consumer;

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
        printlnTitle(original);
        setter.accept(original);
        System.out.printf("Before setting: %s%n", valueBeforeReplacing);
        System.out.printf("After setting: %s%n", original.value);
    }

    public static void replaceObject(Source copy, String methodName) {
        System.out.println(methodName + ": reference after passing - " + refNumber(copy));
        System.out.println(methodName + ": changing reference's object");
        copy = new Source();
        copy.value = NEW_VALUE;
    }

    public static void updateObject(Source copy, String updateObject) {
        System.out.println(updateObject + ": reference after passing - " + refNumber(copy));
        copy.value = NEW_VALUE;
    }

    private static void printlnTitle(Source original) {
        String title = "Reference before passing: ";
        String refWithClassName = refWithClassNameNumber(original);
        printlnLine(title, refWithClassName);
        System.out.println(title + refWithClassName);
    }

    private static void printlnLine(String title, String refWithClassName) {
        System.out.println("-".repeat(title.length()+ refWithClassName.length()));
    }

    private static String refNumber(Source copy) {
        return copy.toString().split("@")[1];
    }

    private static String refWithClassNameNumber(Source copy) {
        String[] split = copy.toString().split("\\.");
        return split[split.length - 1];
    }
}