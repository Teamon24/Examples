package patterns.others.structural.wrapper.adapters.ex1;

public class Storage {
    private Target target;
    public Storage(Target target) {
        this.target = target;
    }
}

class Target {
    private String value;
    public Target(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}

