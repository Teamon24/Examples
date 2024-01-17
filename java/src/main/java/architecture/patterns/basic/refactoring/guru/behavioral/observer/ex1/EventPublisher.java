package architecture.patterns.basic.refactoring.guru.behavioral.observer.ex1;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventPublisher {

    enum EventType {
        OPEN, SAVE
    }

    private final Map<EventType, List<EventSubscriber>> subscribers = new HashMap<>();

    public EventPublisher(EventType... operations) {
        for (EventType operation : operations) {
            this.subscribers.put(operation, new ArrayList<>());
        }
    }

    public void subscribe(EventType eventType, EventSubscriber listener) {
        List<EventSubscriber> users = subscribers.get(eventType);
        users.add(listener);
    }

    public void unsubscribe(EventType eventType, EventSubscriber listener) {
        List<EventSubscriber> users = subscribers.get(eventType);
        users.remove(listener);
    }

    public void notify(EventType eventType, File file) {
        List<EventSubscriber> users = subscribers.get(eventType);
        if (users == null) {
            String message = String.format("There is no subscribers waiting for event '%s'", eventType);
            throw new RuntimeException(message);
        }
        for (EventSubscriber listener : users) {
            listener.update(eventType, file);
        }
    }
}