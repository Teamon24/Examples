package patterns.basic.refactoring.guru.behavioral.chain_of_responsibility.ex1;

/**
 * Конкретный элемент цепи обрабатывает запрос по-своему.
 */
public class RoleCheckMiddleware extends Middleware {

    public boolean process(String email, String password) {
        if (email.equals("admin@example.com")) {
            System.out.println("Hello, admin!");
            return true;
        }
        System.out.println("Hello, user!");
        return nextProcess(email, password);
    }
}