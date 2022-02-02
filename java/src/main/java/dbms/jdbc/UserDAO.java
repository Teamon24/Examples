package dbms.jdbc;

import dbms.jdbc.dbms.SQLStrategy;
import dbms.jdbc.entity.DAO;
import org.apache.commons.lang3.tuple.Pair;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;

import static dbms.jdbc.entity.JDBCUtils.getString;
import static dbms.jdbc.entity.JDBCUtils.setString;
import static java.util.Objects.isNull;
import static utils.PrintUtils.printfln;

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
        printfln("User (id='%s') was updated: new password = '%s'", uuid, password);
    }

    @Override
    public Map<String, Pair<ResultGetter, FieldSetter<UserJdbcEntity>>>
    resultGettersAndEntitySetters() {
        return resultGettersAndEntitySetters;
    }

    @Override
    public Map<String, Pair<FieldGetter<UserJdbcEntity>, StatementSetter<?>>>
    statementSettersAndEntityGetters() {
        return statementSettersAndEntityGetters;
    }

    private final static
    Map<String, Pair<ResultGetter, FieldSetter<UserJdbcEntity>>> resultGettersAndEntitySetters;

    private final static
    Map<String, Pair<FieldGetter<UserJdbcEntity>, StatementSetter<?>>> statementSettersAndEntityGetters;

    static {
        String id = UserJdbcEntity.idColumn;
        String fullName = UserJdbcEntity.fullNameColumn;
        String email = UserJdbcEntity.emailColumn;
        String password = UserJdbcEntity.passwordColumn;

        resultGettersAndEntitySetters = new LinkedHashMap<>() {{
            put(id,       Pair.of(getString(id),       (user, id) -> user.setId((String) id)));
            put(fullName, Pair.of(getString(fullName), (user, fullName) -> user.setFullName((String) fullName)));
            put(password, Pair.of(getString(password), (user, password) -> user.setPassword((String) password)));
            put(email,    Pair.of(getString(email),    (user, mail) -> user.setEmail((String) mail)));
        }};

        statementSettersAndEntityGetters = new LinkedHashMap<>() {{
            put(id,       Pair.of(UserJdbcEntity::getId,       setString(1)));
            put(fullName, Pair.of(UserJdbcEntity::getFullName, setString(2)));
            put(password, Pair.of(UserJdbcEntity::getPassword, setString(3)));
            put(email,    Pair.of(UserJdbcEntity::getEmail,    setString(4)));
        }};
    }
}
