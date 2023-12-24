package architecture.patterns.basic.other_source.creational.factory.simple.ex2;

public class DBReaderCreationStrategy implements ReaderCreationStrategy {
    @Override
    public Reader createReader() {
        return new DataBaseReader();
    }
}
