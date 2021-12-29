package patterns.others.creational.factory.simple.ex2;

public class DataBaseReader implements Reader {
    @Override
    public String read() {
        return "Reading database";
    }
}