package patterns.others.structural.wrapper.adapters.ex1;

public class AdapterClassImpl extends Adaptee implements Adapter {

    public AdapterClassImpl(Source source) {
        super(source);
    }

    @Override
    public String adapt() {
        return convertValue(super.getSource());
    }

    private String convertValue(Source source) {
        return String.valueOf(source.getValue());
    }
}
