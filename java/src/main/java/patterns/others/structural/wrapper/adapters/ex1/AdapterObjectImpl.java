package patterns.others.structural.wrapper.adapters.ex1;

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
