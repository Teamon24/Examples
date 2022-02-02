package dbms.jdbc.entity;

import java.util.List;

public abstract class JdbcEntity<Id> {
    public abstract String getTableName();
    public abstract List<String> getTableColumns();
    public abstract String getIdName();
    public abstract Class<Id> getIdType();
}
