package patterns.basic.refactoring.guru.behavioral.chain_of_responsibility.ex1;

import static utils.PrintUtils.println;

/**
 * Конкретный элемент цепи обрабатывает запрос по-своему.
 */
public class RoleCheckMiddleware extends Middleware {

    public boolean process(String email, String password) {
        if (email.equals("admin@example.com")) {
            println("Hello, admin!");
            return true;
        }
        println("Hello, user!");
        return nextProcess(email, password);
    }
}