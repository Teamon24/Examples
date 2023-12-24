package architecture.patterns.basic.other_source.behavioral.mediator;

/**
 * 28.12.2016.
 * _____________________________________________________________________________________________________________________________________________________________________________________________________________________________
 */
public abstract class Colleague {
    private Mediator mediator;
    private String   name;

    public Colleague(String name, Mediator m) {
        this.mediator = m;
        this.name     = name;
    }

    //send a message via the mediator
    public void send(String message) {
        this.mediator.send(message, this);
    }

    //get access to the mediator
    public Mediator getMediator() {
        return this.mediator;
    }

    public String getName() {
        return name;
    }

    public abstract void receive(String message);
}
