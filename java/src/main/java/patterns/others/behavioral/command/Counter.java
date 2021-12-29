package patterns.others.behavioral.command;

import patterns.others.behavioral.command.commands.Command;

@interface Invoker {}

@Invoker
public class Counter {
    private Command command;

    public void click(){
        this.command.execute();
    }

    public void setCommand(Command command){
        this.command = command;
    }
}

