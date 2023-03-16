package dbms.jdbc;

import dbms.jdbc.entity.JdbcEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UserJdbcEntity extends JdbcEntity<String> {

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
    public String toString() {
        return "UserEntity{" +
            "id='" + id + '\'' +
            ", fullName='" + fullName + '\'' +
            ", password='" + password + '\'' +
            ", email='" + email + '\'' +
            '}';
    }
}
