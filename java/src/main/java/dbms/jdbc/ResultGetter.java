package dbms.jdbc;

import java.sql.ResultSet;
import java.util.function.Function;

public interface ResultGetter extends Function<ResultSet, Object> {
}
