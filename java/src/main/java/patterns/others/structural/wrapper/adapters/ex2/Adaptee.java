package patterns.others.structural.wrapper.adapters.ex2;

public class Adaptee {
    private int specificValue;

    public Adaptee(int specificValue) {
        this.specificValue = specificValue;
    }

    public int getSpecificValue() {
        return specificValue;
    }
}
