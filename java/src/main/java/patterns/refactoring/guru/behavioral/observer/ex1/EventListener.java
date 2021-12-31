package patterns.refactoring.guru.behavioral.observer.ex1;

import java.io.File;

public interface EventListener {
    void update(String eventType, File file);
}