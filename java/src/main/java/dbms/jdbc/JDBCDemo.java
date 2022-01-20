package dbms.jdbc;

import dbms.jdbc.dbms.ConnectionSupplierType;
import dbms.jdbc.dbms.PostgresStrategyBuilder;
import dbms.jdbc.dbms.SQLStrategy;
import utils.DotEnvUtils;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static core.lambda.exception_handling.Throwing.rethrow;
import static core.lambda.exception_handling.ThrowingSupplier.get;

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
    public static void main(String args[])
        throws
        ClassNotFoundException,
        FileNotFoundException
    {
        Class.forName("org.postgresql.Driver");
        Connection connection = get(SQL_STRATEGY::getConnection, SQLException.class);
        rethrow(SQLException.class,
            () -> connection.setAutoCommit(false),
            () -> close(connection)
        );

        rethrow(SQLException.class,
            () -> USER_DAO.truncate(connection));

        List<UserJdbcEntity> usersEntities = Users.USER_ENTITIES;

        rethrow(SQLException.class,
            () -> insert(connection, usersEntities),
            () -> close(connection)
         );

        Savepoint insertingUsers = get(connection::setSavepoint, SQLException.class);

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
            try {
                connection.rollback(insertingUsers);
                connection.commit();
                System.out.println("Rollback to the insertion of users was done.");
            } catch (SQLException ex) {
                close(connection);
                throw new RuntimeException(e);
            }
        }
    }

    private static void insert(Connection connection, List<UserJdbcEntity> usersEntities) throws SQLException {
        for (UserJdbcEntity it : usersEntities) {
            USER_DAO.insert(connection, it);
        }
    }

    private static void close(Connection connection) {
        rethrow(SQLException.class, connection::close);
    }

    private static void printPasswordUpdate(String newPassword, String oldPassword) {
        if (newPassword.equals(oldPassword)) {
            String template = "Password was updated: old = '%s', new = '%s'";
            System.out.println(template.formatted(newPassword, oldPassword));
        }
    }

    private static void handleDelete(Connection connection, String id) throws SQLException {
        Optional
            .ofNullable(USER_DAO.selectById(connection, id))
            .ifPresentOrElse(
                (userJdbcEntity) -> System.out.printf("User (id='%s') was not deleted%n", userJdbcEntity.getId()),
                () -> System.out.printf("User (id='%s') was deleted%n", id));
    }

    private static void handleSelect(Connection connection, String id) throws SQLException {
        Optional
            .ofNullable(USER_DAO.selectById(connection, id))
            .ifPresentOrElse(
                (userJdbcEntity) -> System.out.printf("User (id='%s') was found%n", userJdbcEntity.getId()),
                () -> System.out.printf("User (id='%s') was not found%n", id));
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