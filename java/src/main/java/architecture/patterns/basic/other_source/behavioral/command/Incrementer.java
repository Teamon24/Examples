package architecture.patterns.basic.other_source.behavioral.command;

@interface Receiver {}

@Receiver
public class Incrementer {
    private int i ;

    //do some #click() when invoker
    public int increase() {
        return ++i;
    }

    public int decrease() {
        return --i;
    }

    public int getI() {
        return i;
    }
}

