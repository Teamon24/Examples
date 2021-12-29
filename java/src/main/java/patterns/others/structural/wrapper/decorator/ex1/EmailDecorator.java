package patterns.others.structural.wrapper.decorator.ex1;

public abstract class EmailDecorator implements IEmail {
    //wrapped component
    IEmail originalEmail;
}
