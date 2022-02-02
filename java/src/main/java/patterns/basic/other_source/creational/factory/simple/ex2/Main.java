package patterns.basic.other_source.creational.factory.simple.ex2;


import static utils.PrintUtils.println;

public class Main {
    public static void main(String[] args) {
        ReaderFactory.setReaderCreationStrategy(new XMLReaderCreationStrategy());
        Reader reader = ReaderFactory.getReader();
        println(reader.read());
    }
}
