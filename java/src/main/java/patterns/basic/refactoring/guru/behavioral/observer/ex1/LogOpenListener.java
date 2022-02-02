package patterns.basic.refactoring.guru.behavioral.observer.ex1;

import java.io.File;

import static utils.PrintUtils.println;

public class LogOpenListener implements EventListener {
    private File log;

    public LogOpenListener(String fileName) {
        this.log = new File(fileName);
    }

    @Override
    public void update(String eventType, File file) {
        println("Save to log " + log + ": Someone has performed " + eventType + " operation with the following file: " + file.getName());
    }
}