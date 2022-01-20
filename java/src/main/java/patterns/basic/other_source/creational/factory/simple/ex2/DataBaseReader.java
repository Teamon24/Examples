package patterns.basic.other_source.creational.factory.simple.ex2;

public class DataBaseReader implements Reader {
    @Override
    public String read() {
        return "Reading database";
    }
}