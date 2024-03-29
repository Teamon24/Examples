package architecture.patterns.basic.other_source.structural.decorator.ex1;

public class SecureEmailDecorator extends EmailDecorator {
    private String content;

    public SecureEmailDecorator(IEmail basicEmail) {
        originalEmail = basicEmail;
    }

    @Override
    public String getContents() {
        //  secure original
        content = encrypt(originalEmail.getContents());
        return content;
    }

    private String encrypt(String message) {
        //encrypt the string
        return "encryptedMessage";
    }
}
