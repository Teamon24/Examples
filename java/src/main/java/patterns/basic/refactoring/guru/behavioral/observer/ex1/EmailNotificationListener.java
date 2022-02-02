package patterns.basic.refactoring.guru.behavioral.observer.ex1;

import java.io.File;

import static utils.PrintUtils.println;

public class EmailNotificationListener implements EventListener {
    private String email;

    public EmailNotificationListener(String email) {
        this.email = email;
    }

    @Override
    public void update(String eventType, File file) {
        println("Email to " + email + ": Someone has performed " + eventType + " operation with the following file: " + file.getName());
    }
}