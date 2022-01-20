package patterns.basic.other_source.creational.singleton;

public class Singleton {
    private static Singleton singleton = new Singleton();
    private Singleton(){}
    public static Singleton getInstance(){
        return Singleton.singleton;
    }
}
