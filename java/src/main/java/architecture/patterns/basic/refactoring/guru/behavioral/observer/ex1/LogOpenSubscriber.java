package architecture.patterns.basic.refactoring.guru.behavioral.observer.ex1;

import java.io.File;

public class LogOpenSubscriber implements EventSubscriber {
    private File log;

    public LogOpenSubscriber(String fileName) {
        this.log = new File(fileName);
    }

    @Override
    public void update(EventPublisher.EventType eventType, File file) {
        System.out.println("Save to log " + log + ": Someone has performed " + eventType + " operation with the following file: " + file.getName());
    }

    @Override
    public String toString() {
        return "{\"log\" : " + (log == null ? null : log) + "}";
    }
}