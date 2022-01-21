package aop.ex1;

public class Target {

    @Synchronizes
    public void execute() {
        System.out.println("Execution");
    }
}
