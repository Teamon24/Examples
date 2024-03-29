package dbms.jdbc;

import core.lambda.exception_handling.ThrowingInvoker;
import core.lambda.exception_handling.ThrowingSupplier;
import dbms.jdbc.dbms.ConnectionSupplierType;
import dbms.jdbc.dbms.PostgresStrategyBuilder;
import dbms.jdbc.dbms.SQLStrategy;
import utils.DotEnvUtils;
import utils.PrintUtils;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static core.lambda.exception_handling.ThrowingInvoker.rethrow;

public class JDBCDemo {
    public static final Map<String, String> VARIABLES = new HashMap<>();
    public static final UserDAO USER_DAO;
    private static final SQLStrategy SQL_STRATEGY;

    static {
        try {
            VARIABLES.putAll(DotEnvUtils.variables("C:/Users/teamo/IdeaProjects/Examples/java/docker/vars.sh"));
            SQL_STRATEGY = getStrategy();
            USER_DAO = new UserDAO(SQL_STRATEGY);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String args[]) throws ClassNotFoundException, FileNotFoundException {
        Class.forName("org.postgresql.Driver");
        Connection connection = ThrowingSupplier.rethrow(SQLException.class, SQL_STRATEGY::getConnection);
        ThrowingInvoker.rethrow(SQLException.class,
            () -> connection.setAutoCommit(false),
            () -> close(connection)
        );

        ThrowingInvoker.rethrow(SQLException.class, () -> USER_DAO.truncate(connection));

        List<UserJdbcEntity> usersEntities = Users.USER_ENTITIES;

        ThrowingInvoker.rethrow(SQLException.class,
            () -> insert(connection, usersEntities),
            () -> close(connection)
        );

        Savepoint insertingUsers = ThrowingSupplier.rethrow(SQLException.class, connection::setSavepoint);

        try {
            UserJdbcEntity firstUser = usersEntities.get(0);
            String id = firstUser.getId();
            String newPassword = "1dO7^6Snsl(l7";
            USER_DAO.update(connection, id, newPassword);
            USER_DAO.selectById(connection, id);
            handleSelect(connection, id);

            String oldPassword = firstUser.getPassword();
            printPasswordUpdate(newPassword, oldPassword);

            for (int i = 10; i < 20; i++) {
                UserJdbcEntity removableUser = usersEntities.get(i);
                String searchedUserId = removableUser.getId();
                USER_DAO.deleteById(connection, searchedUserId);
                handleDelete(connection, searchedUserId);
            }
            throw new SQLException();
        } catch (SQLException e) {
            ThrowingInvoker.rethrow(SQLException.class,
                () -> {
                    connection.rollback(insertingUsers);
                    connection.commit();
                    System.out.println("Rollback to the insertion of users was done.");
                },
                () -> close(connection)
            );
        }
    }

    private static void insert(Connection connection, List<UserJdbcEntity> usersEntities) throws SQLException {
        for (UserJdbcEntity it : usersEntities) {
            USER_DAO.insert(connection, it);
        }
    }

    private static void close(Connection connection) {
        ThrowingInvoker.rethrow(SQLException.class, connection::close);
    }

    private static void printPasswordUpdate(String newPassword, String oldPassword) {
        if (newPassword.equals(oldPassword)) {
            String template = "Password was updated: old = '%s', new = '%s'";
            PrintUtils.printfln(template, newPassword, oldPassword);
        }
    }

    private static void handleDelete(Connection connection, String id) throws SQLException {
        USER_DAO.selectById(connection, id)
            .ifPresentOrElse(
                (userJdbcEntity) -> PrintUtils.printfln("User (id='%s') was not deleted%n", userJdbcEntity.getId()),
                () -> PrintUtils.printfln("User (id='%s') was deleted%n", id));
    }

    private static void handleSelect(Connection connection, String id) throws SQLException {
        USER_DAO.selectById(connection, id)
            .ifPresentOrElse(
                (userJdbcEntity) -> PrintUtils.printfln(
                    "User (id='%s') was found%n", userJdbcEntity.getId()),
                () -> PrintUtils.printfln("User (id='%s') was not found%n", id));
    }

    private static SQLStrategy getStrategy() {
        String host = VARIABLES.get("POSTGRES_HOST");
        String port = VARIABLES.get("POSTGRES_PORT");
        String database = VARIABLES.get("POSTGRES_DATABASE");
        String userName = VARIABLES.get("POSTGRES_USER_NAME");
        String password = VARIABLES.get("POSTGRES_PASSWORD");

        return new PostgresStrategyBuilder()
            .host(host)
            .port(Integer.parseInt(port))
            .databaseName(database)
            .username(userName)
            .password(password)
            .schema("examples")
            .connectionSupplier(ConnectionSupplierType.DATA_SOURCE)
            .build();

    }
}