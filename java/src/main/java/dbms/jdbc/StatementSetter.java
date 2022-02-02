package dbms.jdbc;

import java.sql.PreparedStatement;
import java.util.function.BiConsumer;

public interface StatementSetter<T> extends BiConsumer<PreparedStatement, T> {
}
