package aop.ex1;

import static utils.PrintUtils.println;

public class Target {

    @Synchronizes
    public void execute() {
        println("Execution");
    }
}
