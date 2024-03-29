package architecture.patterns.basic.refactoring.guru.behavioral.chain_of_responsibility.ex1;

import static java.lang.System.out;

/**
 * Класс сервера.
 */
public class Server {

    private Middleware middleware;

    /**
     * Клиент подаёт готовую цепочку в сервер. Это увеличивает гибкость и
     * упрощает тестирование класса сервера.
     */
    public void setMiddleware(Middleware middleware) {
        this.middleware = middleware;
    }

    /**
     * Сервер получает email и пароль от клиента и запускает проверку
     * авторизации у цепочки.
     */
    public boolean logIn(String email, String password) {
        if (middleware.process(email, password)) {
            out.println("Authorization have been successful!");

            // Здесь должен быть какой-то полезный код, работающий для
            // авторизированных пользователей.

            return true;
        }
        return false;
    }


}