package dbms.jdbc;

import dbms.jdbc.entity.JDBCUtils;
import dbms.jdbc.dbms.SQLStrategy;
import dbms.jdbc.entity.DAO;
import org.apache.commons.lang3.tuple.Pair;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static java.util.Objects.isNull;

public class UserDAO extends DAO<String, UserJdbcEntity> {

    public static String UPDATE_USER;

    public UserDAO(SQLStrategy sqlStrategy) {
        super(sqlStrategy);
        if (isNull(UPDATE_USER)) {
            UPDATE_USER = String.format("UPDATE %s set password = ? WHERE id = ?", super.getTableName());
        }
    }

    @Override
    public Supplier<UserJdbcEntity> emptyEntityCreator() {
        return UserJdbcEntity::new;
    }

    public void update(Connection con, String uuid, String password) throws SQLException {
        PreparedStatement stmt = con.prepareStatement(UPDATE_USER);
        stmt.setString(1, password);
        stmt.setString(2, uuid);

        stmt.executeUpdate();
        System.out.printf("User (id='%s') was updated: new password = '%s'%n", uuid, password);
    }

    @Override
    public Map<String, Pair<Function<ResultSet, Object>, BiConsumer<UserJdbcEntity, Object>>>
    resultGettersAndEntitySetters() {
        return resultGettersAndEntitySetters;
    }

    @Override
    public Map<String, Pair<BiConsumer<PreparedStatement, Object>, Function<UserJdbcEntity, Object>>>
    statementSettersAndEntityGetters() {
        return statementSettersAndEntityGetters;
    }

    private final static
    Map<String, Pair<Function<ResultSet, Object>, BiConsumer<UserJdbcEntity, Object>>>
        resultGettersAndEntitySetters;

    private final static
    Map<String, Pair<BiConsumer<PreparedStatement, Object>, Function<UserJdbcEntity, Object>>>
        statementSettersAndEntityGetters;

    static {
        resultGettersAndEntitySetters = new LinkedHashMap<>() {{
            put(UserJdbcEntity.idColumn,
                Pair.of(JDBCUtils.getString(UserJdbcEntity.idColumn), (UserJdbcEntity user, Object id) -> user.setId((String) id)));

            put(UserJdbcEntity.fullNameColumn,
                Pair.of(JDBCUtils.getString(UserJdbcEntity.fullNameColumn), (UserJdbcEntity user, Object fullName) -> user.setFullName((String) fullName)));

            put(UserJdbcEntity.passwordColumn,
                Pair.of(JDBCUtils.getString(UserJdbcEntity.passwordColumn), (UserJdbcEntity user, Object password) -> user.setPassword((String) password)));

            put(UserJdbcEntity.emailColumn,
                Pair.of(JDBCUtils.getString(UserJdbcEntity.emailColumn), (UserJdbcEntity user, Object mail) -> user.setEmail((String) mail)));

        }};

        statementSettersAndEntityGetters = new LinkedHashMap<>() {{
            put(UserJdbcEntity.idColumn,
                Pair.of(JDBCUtils.setString(1), UserJdbcEntity::getId));

            put(UserJdbcEntity.fullNameColumn,
                Pair.of(JDBCUtils.setString(2), UserJdbcEntity::getFullName));

            put(UserJdbcEntity.passwordColumn,
                Pair.of(JDBCUtils.setString(3), UserJdbcEntity::getPassword));

            put(UserJdbcEntity.emailColumn,
                Pair.of(JDBCUtils.setString(4), UserJdbcEntity::getEmail));

        }};
    }
}
