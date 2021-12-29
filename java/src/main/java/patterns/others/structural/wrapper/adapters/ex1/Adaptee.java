package patterns.others.structural.wrapper.adapters.ex1;

public class Adaptee {
    private Source source;
    public Adaptee(Source target) {
        this.source = target;
    }
    public Source getSource() {
        return source;
    }
}

class Source {
    private int value;
    public Source(int v) {
        this.value = v;
    }
    public int getValue() {
        return value;
    }
}
