package dbms.jdbc.dbms;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.function.Function;

public abstract class SQLStrategyBuilder {
    String host;
    int port;
    String username;
    String password;
    String databaseName;
    String schema;
    Function<SQLStrategy, Connection> connectionSupplier;

    public SQLStrategyBuilder host(String host) {
        this.host = host;
        return this;
    }

    public SQLStrategyBuilder port(int port) {
        this.port = port;
        return this;
    }

    public SQLStrategyBuilder username(String username) {
        this.username = username;
        return this;
    }

    public SQLStrategyBuilder password(String password) {
        this.password = password;
        return this;
    }

    public SQLStrategyBuilder databaseName(String databaseName) {
        this.databaseName = databaseName;
        return this;
    }

    public SQLStrategyBuilder schema(String schema) {
        this.schema = schema;
        return this;
    }

    public SQLStrategyBuilder connectionSupplier(ConnectionSupplierType connectionSupplierType) {
        switch (connectionSupplierType) {
            case DATA_SOURCE:
                this.connectionSupplier = (SQLStrategy sqlStrategy) -> {
                    try {
                        return sqlStrategy.getDatasource().getConnection();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                };
                return this;

            case DRIVER_MANAGER:
                this.connectionSupplier = (SQLStrategy sqlStrategy) -> {
                    try {
                        String url = sqlStrategy.getUrl();
                        return DriverManager.getConnection(
                            url, sqlStrategy.username, sqlStrategy.password
                        );

                    } catch (SQLException e) {
                        throw new RuntimeException(e.getCause());
                    }
                };
                return this;
        }
        throw new RuntimeException("There is no switch-case for connection supplier type: " + connectionSupplierType);
    }

    public abstract SQLStrategy build();
}