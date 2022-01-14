package jpa.jdbc;

import jpa.jdbc.dbms.SQLStrategy;
import jpa.jdbc.entity.DAO;
import org.apache.commons.lang3.tuple.Pair;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static java.util.Objects.isNull;
import static jpa.jdbc.entity.JDBCUtils.getString;
import static jpa.jdbc.entity.JDBCUtils.setString;

public class UserDAO extends DAO<String, UserEntity> {

    public static String UPDATE_USER;

    public UserDAO(SQLStrategy sqlStrategy) {
        super(sqlStrategy);
        if (isNull(UPDATE_USER)) {
            UPDATE_USER = "UPDATE %s set password = ? WHERE id = ?".formatted(super.getTableName());
        }
    }

    @Override
    public Supplier<UserEntity> emptyEntityCreator() {
        return UserEntity::new;
    }

    public void update(Connection con, String uuid, String password) throws SQLException {
        PreparedStatement stmt = con.prepareStatement(UPDATE_USER);
        stmt.setString(1, password);
        stmt.setString(2, uuid);

        stmt.executeUpdate();
        System.out.printf("User (id='%s') was updated: new password = '%s'%n", uuid, password);
    }

    @Override
    public Map<String, Pair<Function<ResultSet, Object>, BiConsumer<UserEntity, Object>>>
    resultGettersAndEntitySetters() {
        return resultGettersAndEntitySetters;
    }

    @Override
    public Map<String, Pair<BiConsumer<PreparedStatement, Object>, Function<UserEntity, Object>>>
    statementSettersAndEntityGetters() {
        return statementSettersAndEntityGetters;
    }

    private final static
    Map<String, Pair<Function<ResultSet, Object>, BiConsumer<UserEntity, Object>>>
        resultGettersAndEntitySetters;

    private final static
    Map<String, Pair<BiConsumer<PreparedStatement, Object>, Function<UserEntity, Object>>>
        statementSettersAndEntityGetters;

    static {
        resultGettersAndEntitySetters = new LinkedHashMap<>() {{
            put(UserEntity.idColumn,
                Pair.of(getString(UserEntity.idColumn), (UserEntity user, Object id) -> user.setId((String) id)));

            put(UserEntity.fullNameColumn,
                Pair.of(getString(UserEntity.fullNameColumn), (UserEntity user, Object fullName) -> user.setFullName((String) fullName)));

            put(UserEntity.passwordColumn,
                Pair.of(getString(UserEntity.passwordColumn), (UserEntity user, Object password) -> user.setPassword((String) password)));

            put(UserEntity.emailColumn,
                Pair.of(getString(UserEntity.emailColumn), (UserEntity user, Object mail) -> user.setEmail((String) mail)));

        }};

        statementSettersAndEntityGetters = new LinkedHashMap<>() {{
            put(UserEntity.idColumn,
                Pair.of(setString(1), UserEntity::getId));

            put(UserEntity.fullNameColumn,
                Pair.of(setString(2), UserEntity::getFullName));

            put(UserEntity.passwordColumn,
                Pair.of(setString(3), UserEntity::getPassword));

            put(UserEntity.emailColumn,
                Pair.of(setString(4), UserEntity::getEmail));

        }};
    }
}
