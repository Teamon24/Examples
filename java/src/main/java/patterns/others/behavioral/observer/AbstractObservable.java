package patterns.others.behavioral.observer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 29.12.2016.
 * _____________________________________________________________________________________________________________________________________________________________________________________________________________________________
 */
public abstract class AbstractObservable implements Observable {
    private Notifier<Observer> notifier = new Notifier<>();

    public final Map<Action, Notifier> ACTION_MAP = new HashMap<>();

    {
        ACTION_MAP.put(Action.CREATION, notifier);
        ACTION_MAP.put(Action.MODIFICATION, notifier);
    }

    @Override
    public void addObserver(Observer observer) {
        this.notifier.add(observer);
    }

    @Override
    public void deleteObserver(Observer observer) {
        this.notifier.remove(observer);
    }

    public ArrayList<Observer> getNotifier() {
        return notifier;
    }

    @Override
    public void notifyObserver(Observer observer, Object object, Action objectAction) {
        if (objectAction == Action.CREATION)
            observer.objectCreated(object);

        if (objectAction == Action.MODIFICATION)
            observer.objectModified(object);
    }

    @Override
    public void notifyObservers(Object object, Action objectAction) {
        if (objectAction == Action.CREATION)
            notifier.notifyAllAboutCreation(object);

        if (objectAction == Action.MODIFICATION)
            notifier.notifyAllAboutModification(object);
    }

    public final class Notifier<T extends Observer> extends ArrayList<T> {
        public void notifyAllAboutCreation(Object obj) {
            for (Iterator<T> iter = this.iterator(); iter.hasNext(); )
                iter.next().objectCreated(obj);
        }

        public void notifyAllAboutModification(Object obj) {
            for (Iterator<T> iter = this.iterator(); iter.hasNext(); )
                iter.next().objectModified(obj);
        }
    }
}
