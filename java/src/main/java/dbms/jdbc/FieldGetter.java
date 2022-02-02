package dbms.jdbc;

import dbms.jdbc.entity.JdbcEntity;

import java.util.function.Function;

public interface FieldGetter<E extends JdbcEntity<?>> extends Function<E, Object> {
}