package dbms.jdbc;

import dbms.jdbc.entity.JdbcEntity;

import java.util.function.BiConsumer;

public interface FieldSetter<E extends JdbcEntity<?>> extends BiConsumer<E, Object> {
}

