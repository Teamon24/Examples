package patterns.basic.other_source.creational.factory.simple.ex2;


import utils.PrintUtils;

import static java.lang.System.out;

public class Main {
    public static void main(String[] args) {
        ReaderFactory.setReaderCreationStrategy(new XMLReaderCreationStrategy());
        Reader reader = ReaderFactory.getReader();
        System.out.println(reader.read());
    }
}
