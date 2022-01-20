package patterns.basic.other_source.creational.prototype;

public class Client {
    public static void main(String[] args) {
        ItemRegistry registry = new ItemRegistry();
        Book myBook = (Book) registry.createBasicItem("Book");
        myBook.setTitle("Custom Title");
    }
}