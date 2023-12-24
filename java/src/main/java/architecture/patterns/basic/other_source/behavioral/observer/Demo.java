package architecture.patterns.basic.other_source.behavioral.observer;

    import lombok.val;

    import java.util.function.Consumer;

    import static java.lang.System.out;

public class Demo {

    public static void main(String[] args) {
        val observable = new AbstractObservable() {};

        observable.addObserver(observer("1"));
        observable.addObserver(observer("2"));
        observable.addObserver(observer("3"));

        Object object = new Object();
        observable.notifyObservers(object, Action.CREATION);
        observable.notifyObservers(object, Action.MODIFICATION);
    }

    private static Observer observer(String number) {
        return observer(
            o -> out.println("observer " + number + ": created - " + o),
            o -> out.println("observer " + number + ": modified - " + o));
    }


    private static Observer observer(Consumer<Object> onCreated, Consumer<Object> onModified) {
        return new Observer() {
            @Override public void objectCreated(Object obj) { onCreated.accept(obj); }
            @Override public void objectModified(Object obj) { onModified.accept(obj); }
        };
    }
}
