package jpa.jdbc.dbms;

import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.function.Function;


public class PostgresqlStrategy extends SQLStrategy {

    private static final String URL_TEMPLATE = "jdbc:postgresql://%s:%s/%s?currentSchema=%s";
    private final PGSimpleDataSource PG_SIMPLE_DATA_SOURCE = new PGSimpleDataSource();

    public PostgresqlStrategy(String host,
                              int port,
                              String username,
                              String password,
                              String databaseName,
                              String currentSchema,
                              Function<SQLStrategy, Connection> connectionSupplier)
    {
        super(host, port, username, password, databaseName, currentSchema, connectionSupplier);
        PG_SIMPLE_DATA_SOURCE.setServerNames(new String[]{host});
        PG_SIMPLE_DATA_SOURCE.setPortNumbers(new int[]{port});
        PG_SIMPLE_DATA_SOURCE.setUser(username);
        PG_SIMPLE_DATA_SOURCE.setPassword(password);
        PG_SIMPLE_DATA_SOURCE.setDatabaseName(databaseName);
        PG_SIMPLE_DATA_SOURCE.setCurrentSchema(currentSchema);
    }

    @Override
    public String selectByIdsQuery(String tableName, String idName) {
        return "SELECT * FROM %s WHERE %s = ANY (?)".formatted(tableName, idName);
    }

    @Override
    public String selectByIdQuery(String tableName, String idName) {
        return "SELECT * FROM %s WHERE %s = ?".formatted(tableName, idName);
    }

    @Override
    public String deleteByIdQuery(String tableName, String idName) {
        return "DELETE FROM %s WHERE %s = ?".formatted(tableName, idName);
    }

    @Override
    public String insertUserQuery(String tableName) {
        return "INSERT INTO %s VALUES(?, ?, ?, ?)".formatted(tableName);
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
        return URL_TEMPLATE.formatted(this.host, this.port, this.databaseName, this.schema);
    }

    @Override
    public DataSource getDatasource() {
        return PG_SIMPLE_DATA_SOURCE;
    }
}
