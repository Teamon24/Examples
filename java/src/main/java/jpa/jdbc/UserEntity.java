package jpa.jdbc;

import jpa.jdbc.entity.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.function.BiConsumer;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UserEntity extends Entity<String> {

    public static final String idColumn = "id";
    private String id;

    public static final String fullNameColumn = "full_name";
    private String fullName;

    public static final String passwordColumn = "password";
    private String password;

    public static final String emailColumn = "email";
    private String email;


    @Override
    public String getTableName() {
        return "user";
    }

    @Override
    public List<String> getTableColumns() {
        return List.of(idColumn, fullNameColumn, passwordColumn, emailColumn);
    }

    @Override
    public String getIdName() {
        return idColumn;
    }

    @Override
    public Class<String> getIdType() {
        return String.class;
    }

    @Override
    public BiConsumer<PreparedStatement, String> idResultSetter() {
        return (preparedStatement, id) -> {
            try {
                preparedStatement.setString(1, id);
            } catch (SQLException e) {
                throw new RuntimeException(e.getCause());
            }
        };
    }

    @Override
    public String toString() {
        return "UserEntity{" +
            "id='" + id + '\'' +
            ", fullName='" + fullName + '\'' +
            ", password='" + password + '\'' +
            ", email='" + email + '\'' +
            '}';
    }
}
