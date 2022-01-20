package dbms.jdbc.dbms;

import lombok.Getter;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.function.Function;

public abstract class SQLStrategy {

    protected String host;
    protected int port;
    protected String username;
    protected String password;
    protected String databaseName;
    @Getter
    protected String schema;
    protected Function<SQLStrategy, Connection> connectionSupplier;

    protected SQLStrategy(String host,
                          int port,
                          String username,
                          String password,
                          String databaseName,
                          String schema,
                          Function<SQLStrategy, Connection> connectionSupplier) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
        this.databaseName = databaseName;
        this.schema = schema;
        this.connectionSupplier = connectionSupplier;
    }

    public abstract String selectByIdsQuery(String tableName, String idName);
    public abstract String selectByIdQuery(String tableName, String idName);
    public abstract String deleteByIdQuery(String tableName, String idName);
    public abstract String insertUserQuery(String tableName, List<String> columns);

    public abstract String getIntegerTypeName();
    public abstract String getVarcharTypeName();
    public abstract String getUrl();
    public abstract DataSource getDatasource();

    public Connection getConnection() throws SQLException {
        return this.connectionSupplier.apply(this);
    }
}
