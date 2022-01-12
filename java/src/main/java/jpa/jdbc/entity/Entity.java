package jpa.jdbc.entity;

import java.sql.PreparedStatement;
import java.util.function.BiConsumer;

public abstract class Entity<Id, T> {


    public abstract String getTableName();
    public abstract String getIdName();

    public abstract Class<Id> getIdType();
    public abstract BiConsumer<PreparedStatement, Id> idResultSetter();

}
