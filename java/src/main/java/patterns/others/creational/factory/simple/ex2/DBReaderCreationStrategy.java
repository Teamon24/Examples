package patterns.others.creational.factory.simple.ex2;

public class DBReaderCreationStrategy implements ReaderCreationStrategy {
    @Override
    public Reader createReader() {
        return new DataBaseReader();
    }
}
