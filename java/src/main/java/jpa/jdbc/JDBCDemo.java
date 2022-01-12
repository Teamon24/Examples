package jpa.jdbc;

import jpa.jdbc.dbms.PostgresStrategyBuilder;
import jpa.jdbc.dbms.SQLStrategy;
import utils.DotEnvUtils;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static jpa.jdbc.dbms.ConnectionSupplierType.DATA_SOURCE;

public class JDBCDemo {
    public static final Map<String, String> VARIABLES = new HashMap<>();
    private static final SQLStrategy SQL_STRATEGY;
    public static final UserDAO USER_DAO;
    public static final String SCHEMA = "examples";

    static {
        try {
            VARIABLES.putAll(DotEnvUtils.getVariables("jpa/postgres/vars.sh"));
            SQL_STRATEGY = getStrategy();
            USER_DAO = new UserDAO(SQL_STRATEGY);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String args[]) throws ClassNotFoundException, FileNotFoundException {
        Class.forName("org.postgresql.Driver");

        try (Connection connection = SQL_STRATEGY.getConnection()) {
            connection.setAutoCommit(false);
            connection.createStatement().execute("CREATE SCHEMA IF NOT EXISTS " + SCHEMA);
            connection.commit();

            USER_DAO.truncate(connection);
            List<UserEntity> usersEntities = Users.USER_ENTITIES;
            for (UserEntity it : usersEntities) {
                USER_DAO.insert(connection, it);
            }

            String id = usersEntities.get(0).getId();
            String newPassword = "1dO7^6Snsl(l7";
            USER_DAO.update(connection, id, newPassword);
            USER_DAO.selectById(connection, id).getPassword().equals(newPassword);

            UserEntity searchedUser = usersEntities.get(1);
            USER_DAO.selectById(connection, searchedUser.getId());
            USER_DAO.deleteById(connection, searchedUser.getId());
            Optional
                .ofNullable(USER_DAO.selectById(connection, searchedUser.getId()))
                .ifPresentOrElse(
                    (ignored) -> System.out.printf("User (id='%s') was not found%n", searchedUser.getId()),
                    () -> {
                    });

            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
    }

    private static SQLStrategy getStrategy() {
        String host = VARIABLES.get("HOST");
        String port = VARIABLES.get("PORT");
        String database = VARIABLES.get("DATABASE");
        String userName = VARIABLES.get("USER_NAME");
        String password = VARIABLES.get("PASSWORD");

        return new PostgresStrategyBuilder()
            .host(host)
            .port(Integer.parseInt(port))
            .databaseName(database)
            .username(userName)
            .password(password)
            .schema(SCHEMA)
            .connectionSupplier(DATA_SOURCE)
            .build();

    }
}