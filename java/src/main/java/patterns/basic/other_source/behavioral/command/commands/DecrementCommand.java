package patterns.basic.other_source.behavioral.command.commands;


import patterns.basic.other_source.behavioral.command.Incrementer;

/**
 * 29.12.2016.
 * _____________________________________________________________________________________________________________________________________________________________________________________________________________________________
 */
public class DecrementCommand extends Command {
    private Incrementer incrementer;

    public DecrementCommand(Incrementer incrementer) {
        this.incrementer = incrementer;
    }

    @Override
    public void execute() {
        this.incrementer.decrease();
    }
}
