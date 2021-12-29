package patterns.others.behavioral.command.commands;


import patterns.others.behavioral.command.Incrementer;

/**
 * 29.12.2016.
 * _____________________________________________________________________________________________________________________________________________________________________________________________________________________________
 */
public class IncrementCommand extends Command {
    private Incrementer incrementer;

    public IncrementCommand(Incrementer incrementer) {
        this.incrementer = incrementer;
    }

    @Override
    public void execute() {
        incrementer.increase();
    }
}
