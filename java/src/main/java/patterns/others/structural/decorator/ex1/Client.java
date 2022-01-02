package patterns.others.structural.decorator.ex1;

public class Client {
    public static void main(String[] args) {
        EmailSender emailSender = new EmailSender();
        Email email = new Email("Message for Bob");
        ExternalEmailDecorator externalEmailDecorator = new ExternalEmailDecorator(email);
        SecureEmailDecorator securedEmailDecorator = new SecureEmailDecorator(externalEmailDecorator);
        emailSender.sendEmail(securedEmailDecorator);
    }
}
