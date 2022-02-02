package dbms.jdbc.entity;

import dbms.jdbc.ResultGetter;
import dbms.jdbc.StatementSetter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Function;

public class JDBCUtils {
    public static ResultGetter getString(String column) {
        return (resultSet) -> {
            try {
                return resultSet.getString(column);
            } catch (SQLException e) {
                throw new RuntimeException(e.getCause());
            }
        };
    }

    public static ResultGetter getInt(String column) {
        return (resultSet) -> {
            try {
                return resultSet.getInt(column);
            } catch (SQLException e) {
                throw new RuntimeException(e.getMessage(), e.getCause());
            }
        };
    }

    public static StatementSetter<String> setString(Integer index) {
        return (prepared, value) -> {
            try {
                prepared.setString(index, value);
            } catch (SQLException e) {
                throw new RuntimeException(e.getMessage(), e.getCause());
            }
        };
    }
}
