package patterns.basic.other_source.creational.factory.simple.ex2;


public class ReaderFactory {

    private static ReaderCreationStrategy readerCreationStrategy;

    public static void setReaderCreationStrategy(ReaderCreationStrategy readerCreationStrategy) {
        ReaderFactory.readerCreationStrategy = readerCreationStrategy;
    }

    public static ReaderCreationStrategy getReaderCreationStrategy() {
        return readerCreationStrategy;
    }

    public static Reader getReader() {
        return ReaderFactory.getReaderCreationStrategy().createReader();
    }

}
