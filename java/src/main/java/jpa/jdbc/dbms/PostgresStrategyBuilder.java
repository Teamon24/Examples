package jpa.jdbc.dbms;

public class PostgresStrategyBuilder extends SQLStrategyBuilder {

    @Override
    public SQLStrategy build() {
        return new PostgresqlStrategy(
            super.host,
            super.port,
            super.username,
            super.password,
            super.databaseName,
            super.schema,
            super.getConnection
        );
    }
}
