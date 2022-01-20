package patterns.basic.other_source.structural.adapter.ex1;

public class AdapterObjectImpl implements Adapter {

    private Adaptee adaptee;

    public AdapterObjectImpl(Adaptee adaptee) {
        this.adaptee = adaptee;
    }

    @Override
    public String adapt() {
        return convertValue(adaptee.getSource());
    }

    private String convertValue(Source source) {
        return String.valueOf(source.getValue());
    }
}
