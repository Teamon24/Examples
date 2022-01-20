package patterns.basic.refactoring.guru.behavioral.chain_of_responsibility.ex1;

/**
 * Конкретный элемент цепи обрабатывает запрос по-своему.
 */
public class UserExistsMiddleware extends Middleware {
    private final UserService userService = new UserService();

    public boolean process(String email, String password) {
        if (!userService.hasEmail(email)) {
            System.out.println("This email is not registered!");
            return false;
        }
        if (!userService.isValidPassword(email, password)) {
            System.out.println("Wrong password!");
            return false;
        }
        return nextProcess(email, password);
    }
}