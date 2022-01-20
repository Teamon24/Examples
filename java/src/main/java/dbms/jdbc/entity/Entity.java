package dbms.jdbc.entity;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.function.BiConsumer;

public abstract class Entity<Id> {
    public abstract String getTableName();
    public abstract List<String> getTableColumns();
    public abstract String getIdName();
    public abstract Class<Id> getIdType();
    public abstract BiConsumer<PreparedStatement, Id> idResultSetter();
}
