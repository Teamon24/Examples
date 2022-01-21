package dbms.jpa.ex1.programmatical.provider;

import dbms.jpa.ex1.programmatical.driver.DriverProperties;
import dbms.jpa.ex1.programmatical.driver.DriverType;
import dbms.jpa.ex1.programmatical.driver.DriverPropertiesFactory;

public class ProviderPropertiesFactory {

    public static ProviderProperties createProps(ProviderType type, DriverProperties driverProperties) {
        switch (type) {
            case HIBERNATE: {
                return new HibernateProperties(driverProperties);
            }
            case ECLIPSE_LINK: {
                return new EclipseLinkProperties(driverProperties);
            }
        }
        throw new UnsupportedOperationException("Switch-case is not implemented for type: " + type);
    }

    public static ProviderProperties createProps(
        ProviderType providerType,
        DriverType type,
        String host,
        int port,
        String databaseName,
        String userName,
        String password,
        String schema)
    {
        DriverProperties driverProperties = DriverPropertiesFactory.create(
            type, host, port, databaseName, userName, password, schema
        );

        return createProps(providerType, driverProperties);
    }
}
