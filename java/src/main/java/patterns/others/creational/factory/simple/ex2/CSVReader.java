package patterns.others.creational.factory.simple.ex2;


public class CSVReader implements Reader {
    @Override
    public String read() {
        return "CSV file reading";
    }
}
