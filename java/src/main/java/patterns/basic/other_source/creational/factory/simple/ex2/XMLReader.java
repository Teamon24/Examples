package patterns.basic.other_source.creational.factory.simple.ex2;


public class XMLReader implements Reader {
    @Override
    public String read() {
        return "XML file reader";
    }
}