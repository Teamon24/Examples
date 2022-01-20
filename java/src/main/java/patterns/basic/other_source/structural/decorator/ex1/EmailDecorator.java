package patterns.basic.other_source.structural.decorator.ex1;

public abstract class EmailDecorator implements IEmail {
    //wrapped component
    IEmail originalEmail;
}
