package architecture.patterns.basic.refactoring.guru.behavioral.observer.ex1;

import java.io.File;

public interface EventSubscriber {
    void update(EventPublisher.EventType eventType, File file);
}