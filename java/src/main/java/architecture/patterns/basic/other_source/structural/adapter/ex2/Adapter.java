package architecture.patterns.basic.other_source.structural.adapter.ex2;

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
