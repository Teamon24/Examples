package patterns.others.structural.adapter.ex2;


public class Test {
    public static void main(String[] args) {
        int specificValue = 100;

        Adaptee adaptee = new Adaptee(specificValue);
        Target target = new Adapter(adaptee);

        String adaptedValue = target.getValue();

        System.out.printf(
            "before: %s, after: %s%n",
            specificValue,
            adaptedValue
        );
    }
}
