package patterns.others.creational.factory.simple.ex2;


public class Main {
    public static void main(String[] args) {
        ReaderFactory.setReaderCreationStrategy(new XMLReaderCreationStrategy());
        Reader reader = ReaderFactory.getReader();
        System.out.println(reader.read());
    }
}
