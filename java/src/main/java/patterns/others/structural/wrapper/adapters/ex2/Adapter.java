package patterns.others.structural.wrapper.adapters.ex2;

public class Adapter implements Target {
    private Adaptee adaptee;

    public Adapter(Adaptee adaptee) {
        this.adaptee = adaptee;
    }

    @Override
    public String getValue() {
        return String.valueOf(adaptee.getSpecificValue());
    }
}
