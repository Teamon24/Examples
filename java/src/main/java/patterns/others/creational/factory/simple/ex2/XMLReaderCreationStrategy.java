package patterns.others.creational.factory.simple.ex2;

public class XMLReaderCreationStrategy implements ReaderCreationStrategy {
    @Override
    public Reader createReader() {
        return new XMLReader();
    }
}
