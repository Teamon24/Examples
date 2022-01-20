package dbms.jpa.ex1.programmatical.provider;

import dbms.jpa.ex1.programmatical.driver.DriverType;
import org.apache.commons.lang3.StringUtils;

public class ProviderPropertiesBuilder {

    private ProviderType provider;
    private DriverType driver;
    private String host;
    private int port;
    private String databaseName;
    private String userName;
    private String password;
    private String schema;

    public ProviderPropertiesBuilder provider(ProviderType type) {
        this.provider = type;
        return this;
    }

    public ProviderPropertiesBuilder driver(DriverType type) {
        this.driver = type;
        return this;
    }

    public ProviderPropertiesBuilder host(String host) {
        this.host = host;
        return this;
    }

    public ProviderPropertiesBuilder port(int port) {
        this.port = port;
        return this;
    }

    public ProviderPropertiesBuilder databaseName(String databaseName) {
        this.databaseName = databaseName;
        return this;
    }

    public ProviderPropertiesBuilder userName(String userName) {
        this.userName = userName;
        return this;
    }

    public ProviderPropertiesBuilder password(String password) {
        this.password = password;
        return this;
    }

    public ProviderPropertiesBuilder schema(String schema) {
        this.schema = schema;
        return this;
    }

    public ProviderProperties build() {
        if (StringUtils.isEmpty(this.userName)) throw new RuntimeException("While building Provider properties, userName should not be null");
        if (StringUtils.isEmpty(this.databaseName)) throw new RuntimeException("While building Provider properties, database should not be null");

        return ProviderPropertiesFactory.createProps(
            this.provider == null ? ProviderType.HIBERNATE : this.provider,
            this.driver == null ? DriverType.POSTGRES : this.driver,
            this.host == null ? "localhost" : this.host,
            this.port == 0 ? 5432 : this.port,
            this.databaseName,
            this.userName,
            this.password == null ? "" : this.password,
            this.schema == null ? "" : this.schema
        );
    }
}
