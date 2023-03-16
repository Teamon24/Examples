package aop.ex1;

import static java.lang.System.out;

public class Target {

    @Synchronizes
    public void execute() {
        out.println("Execution");
    }
}
