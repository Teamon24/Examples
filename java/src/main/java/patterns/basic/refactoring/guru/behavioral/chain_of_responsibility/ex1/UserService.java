package patterns.basic.refactoring.guru.behavioral.chain_of_responsibility.ex1;

import java.util.HashMap;
import java.util.Map;

public class UserService {
    private static Map<String, String> users = new HashMap<>();
    static {
        register("admin@example.com", "admin_pass");
        register("user@example.com", "user_pass");
    }

    public static void register(String email, String password) {
        users.put(email, password);
    }

    public boolean hasEmail(String email) {
        return users.containsKey(email);
    }

    public boolean isValidPassword(String email, String password) {
        return users.get(email).equals(password);
    }
}
