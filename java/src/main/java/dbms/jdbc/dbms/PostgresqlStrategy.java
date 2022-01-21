package dbms.jdbc.dbms;

import org.apache.commons.lang3.StringUtils;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;


public class PostgresqlStrategy extends SQLStrategy {

    private static final String URL_TEMPLATE = "jdbc:postgresql://%s:%s/%s?currentSchema=%s";
    private final PGSimpleDataSource PG_SIMPLE_DATA_SOURCE;

    public PostgresqlStrategy(String host,
                              int port,
                              String username,
                              String password,
                              String databaseName,
                              String currentSchema,
                              Function<SQLStrategy, Connection> connectionSupplier)
    {
        super(host, port, username, password, databaseName, currentSchema, connectionSupplier);
        PG_SIMPLE_DATA_SOURCE = new PGSimpleDataSource();
        PG_SIMPLE_DATA_SOURCE.setServerNames(new String[]{host});
        PG_SIMPLE_DATA_SOURCE.setPortNumbers(new int[]{port});
        PG_SIMPLE_DATA_SOURCE.setUser(username);
        PG_SIMPLE_DATA_SOURCE.setPassword(password);
        PG_SIMPLE_DATA_SOURCE.setDatabaseName(databaseName);
        PG_SIMPLE_DATA_SOURCE.setCurrentSchema(currentSchema);
    }

    @Override
    public String selectByIdsQuery(String tableName, String idName) {
        return String.format("SELECT * FROM %s WHERE %s = ANY (?)", tableName, idName);
    }

    @Override
    public String selectByIdQuery(String tableName, String idName) {
        return String.format("SELECT * FROM %s WHERE %s = ?", tableName, idName);
    }

    @Override
    public String deleteByIdQuery(String tableName, String idName) {
        return String.format("DELETE FROM %s WHERE %s = ?", tableName, idName);
    }

    @Override
    public String insertUserQuery(String tableName, List<String> columns) {
        return String.format("INSERT INTO %s (%s) VALUES(%s)",
            tableName, join(columns), placeholders(columns.size())
        );
    }

    private String placeholders(int size) {
        return join(Collections.nCopies(size, "?"));
    }

    private String join(List<String> columns) {
        return StringUtils.joinWith(",", columns).replaceAll("[\\[\\]]", "");
    }

    @Override
    public String getIntegerTypeName() {
        return "INTEGER";
    }

    @Override
    public String getVarcharTypeName() {
        return "VARCHAR";
    }

    @Override
    public String getUrl() {
        return String.format(URL_TEMPLATE, this.host, this.port, this.databaseName, this.schema);
    }

    @Override
    public DataSource getDatasource() {
        return PG_SIMPLE_DATA_SOURCE;
    }
}
