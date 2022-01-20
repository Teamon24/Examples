package patterns.basic.other_source.behavioral.mediator;

/**
 * 28.12.2016.
 * _____________________________________________________________________________________________________________________________________________________________________________________________________________________________
 */
public interface Mediator {
    void send(String message, Colleague colleague);
}
