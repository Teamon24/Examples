package architecture.patterns.basic.other_source.behavioral.command.commands;


import architecture.patterns.basic.other_source.behavioral.command.Incrementer;

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
