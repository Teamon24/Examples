package architecture.patterns.basic.refactoring.guru.behavioral.observer.ex1;

import lombok.AllArgsConstructor;

import java.io.File;

@AllArgsConstructor
public class EmailNotificationSubscriber implements EventSubscriber {
    private final String email;

    @Override
    public void update(EventPublisher.EventType eventType, File file) {
        System.out.println("Email to " + email + ": someone has performed " + eventType + " operation with the following file: " + file.getName());
    }
}