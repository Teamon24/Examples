package patterns.others.structural.wrapper.adapters.ex1;

public class Test {

    public static void main(String[] args) {
        testClassAdapter();
        testObjectAdapter();
    }

    private static void testObjectAdapter() {
        Source source = new Source(100);
        Adaptee adaptee = new Adaptee(source);
        
        Adapter adapter = new AdapterObjectImpl(adaptee);

        String adapted = adapter.adapt();

        Target target = new Target(adapted);
        Storage storage = new Storage(target);
        System.out.println(getMessage(source, target));
    }

    private static void testClassAdapter() {
        Source source = new Source(100);
        Adapter adapter = new AdapterClassImpl(source);
        String adaptedValue = adapter.adapt();
        Target target = new Target(adaptedValue);
        System.out.println(getMessage(source, target));
    }

    private static String getMessage(Source source, Target target) {
        int sourceValue = source.getValue();
        String targetValue = target.getValue();
        return String.format("source value: %s target value: %s", sourceValue, targetValue);
    }
}
