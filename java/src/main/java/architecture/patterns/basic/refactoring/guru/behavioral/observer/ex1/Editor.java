package architecture.patterns.basic.refactoring.guru.behavioral.observer.ex1;

import architecture.patterns.basic.refactoring.guru.behavioral.observer.ex1.EventPublisher.EventType;

import java.io.File;

import static architecture.patterns.basic.refactoring.guru.behavioral.observer.ex1.EventPublisher.EventType.OPEN;
import static architecture.patterns.basic.refactoring.guru.behavioral.observer.ex1.EventPublisher.EventType.SAVE;

public class Editor {
    public EventPublisher events;
    private File file;

    public Editor() {
        this.events = new EventPublisher(EventType.values());
    }

    public void openFile(String filePath) {
        this.file = new File(filePath);
        events.notify(OPEN, file);
    }

    public void saveFile() throws Exception {
        if (this.file != null) {
            events.notify(SAVE, file);
        } else {
            throw new Exception("Please open a file first.");
        }
    }
}