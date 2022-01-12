package jpa.jdbc.entity;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class JDBCUtils {
    public static Function<ResultSet, Object> getString(String column) {
        return (resultSet) -> {
            try {
                return resultSet.getString(column);
            } catch (SQLException e) {
                throw new RuntimeException(e.getCause());
            }
        };
    }

    public static Function<ResultSet, Object> integer(String column) {
        return (resultSet) -> {
            try {
                return resultSet.getInt(column);
            } catch (SQLException e) {
                throw new RuntimeException(e.getCause());
            }
        };
    }

    public static BiConsumer<PreparedStatement, Object> setString(Integer index) {
        return (prepared, value) -> {
            try {
                prepared.setString(index, (String) value);
            } catch (SQLException e) {
                throw new RuntimeException(e.getCause());
            }
        };
    }
}
