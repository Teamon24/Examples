package inheritance.ex2;

public interface IInterface {
    //default method can use this-reference.
    default String a() {
        return this.toString();
    }
}

class CClass implements IInterface {

    @Override
    public String toString() {
        return "CClass{}";
    }

    public String invokerDefault() {
        return IInterface.super.a();
    }

    public static void main(String[] args) {
        System.out.println(new CClass().invokerDefault());
    }
}

class C2Class {
    void invokeDefault() {
        /*Object clone = IInterface.super.clone();*/ //Compilation error
    }
}
