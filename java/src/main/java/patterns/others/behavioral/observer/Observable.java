package patterns.others.behavioral.observer;

/**
 * 29.12.2016.
 * _____________________________________________________________________________________________________________________________________________________________________________________________________________________________
 */
public interface Observable {

    void addObserver(Observer observer);
    void deleteObserver(Observer observer);

    void notifyObserver(Observer observer, Object object, Action objectAction);
    void notifyObservers(Object object, Action objectAction);
}
