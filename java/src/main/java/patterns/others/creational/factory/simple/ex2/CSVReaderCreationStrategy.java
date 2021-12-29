package patterns.others.creational.factory.simple.ex2;

public class CSVReaderCreationStrategy implements ReaderCreationStrategy {

    @Override
    public Reader createReader() {
        return new CSVReader();
    }
}
