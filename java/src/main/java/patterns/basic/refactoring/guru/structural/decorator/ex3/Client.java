package patterns.basic.refactoring.guru.structural.decorator.ex3;

import static utils.PrintUtils.println;

public class Client {
    public static void main(String[] args) {
        String salaryRecords = "Name,Salary\nJohn Smith,100000\nSteven Jobs,912000";
        DataSourceDecorator encoded = new CompressionDecorator(
                                         new EncryptionDecorator(
                                             new FileDataSource("out/OutputDemo.txt")));
        encoded.writeData(salaryRecords);
        DataSource plain = new FileDataSource("out/OutputDemo.txt");

        println("- Input ----------------");
        println(salaryRecords);
        println("- Encoded --------------");
        println(plain.readData());
        println("- Decoded --------------");
        println(encoded.readData());
    }
}