package jpa.jdbc;

import com.github.javafaker.Faker;
import com.github.javafaker.Internet;
import com.github.javafaker.Name;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Users {
    public static final List<UserEntity> USER_ENTITIES = new ArrayList<>();
    private static final Faker FAKER = new Faker();

    public static final Name NAMES = FAKER.name();

    public static final Internet INTERNET = FAKER.internet();

    static {
        for (int i = 0; i < 50; i++) {
            USER_ENTITIES.add(new UserEntity(uuid(), fullName(), password(), email()));
        }
    }

    private static String fullName() {
        return NAMES.fullName();
    }

    private static String email() {
        return INTERNET.emailAddress();
    }

    private static String password() {
        return INTERNET.password(12, 13, true, true, true);
    }

    private static String uuid() {
        return UUID.randomUUID().toString();
    }
}
