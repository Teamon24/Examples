package architecture.patterns.basic.other_source.structural.adapter.ex2;


import static utils.PrintUtils.printfln;

public class Test {
    public static void main(String[] args) {
        int specificValue = 100;

        Adaptee adaptee = new Adaptee(specificValue);
        Target target = new Adapter(adaptee);

        String adaptedValue = target.getValue();

        printfln(
            "before: %s, after: %s",
            specificValue,
            adaptedValue
        );
    }
}
