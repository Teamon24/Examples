package architecture.patterns.basic.other_source.structural.adapter.ex2;

public class Adaptee {
    private int specificValue;

    public Adaptee(int specificValue) {
        this.specificValue = specificValue;
    }

    public int getSpecificValue() {
        return specificValue;
    }
}
