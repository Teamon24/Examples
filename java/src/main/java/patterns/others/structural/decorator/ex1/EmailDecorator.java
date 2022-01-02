package patterns.others.structural.decorator.ex1;

public abstract class EmailDecorator implements IEmail {
    //wrapped component
    IEmail originalEmail;
}
