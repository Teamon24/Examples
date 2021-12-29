package patterns.others.structural.wrapper.decorator.ex1;

public class Email implements IEmail {
    private String content;

    public Email(String content) {
        this.content = content;
    }

    @Override
    public String getContents() {
        //general email stuff
        return content;
    }
}