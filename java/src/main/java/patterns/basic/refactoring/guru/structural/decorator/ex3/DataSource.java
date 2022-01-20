package patterns.basic.refactoring.guru.structural.decorator.ex3;

public interface DataSource {
    void writeData(String data);

    String readData();
}