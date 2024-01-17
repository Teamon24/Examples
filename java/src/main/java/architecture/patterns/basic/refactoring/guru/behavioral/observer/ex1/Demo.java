package architecture.patterns.basic.refactoring.guru.behavioral.observer.ex1;

import static architecture.patterns.basic.refactoring.guru.behavioral.observer.ex1.EventPublisher.EventType.OPEN;
import static architecture.patterns.basic.refactoring.guru.behavioral.observer.ex1.EventPublisher.EventType.SAVE;

public class Demo {
    public static void main(String[] args) {
        Editor editor = new Editor();
        editor.events.subscribe(OPEN, new LogOpenSubscriber("/path/to/log/file.txt"));
        editor.events.subscribe(SAVE, new EmailNotificationSubscriber("admin@example.com"));

        try {
            editor.openFile("test.txt");
            editor.saveFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}