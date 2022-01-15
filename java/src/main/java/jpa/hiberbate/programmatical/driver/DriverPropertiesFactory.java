package jpa.hiberbate.programmatical.driver;

import static jpa.hiberbate.programmatical.driver.DriverProperties.PASSWORD_PROP;
import static jpa.hiberbate.programmatical.driver.DriverProperties.URL_PROP;
import static jpa.hiberbate.programmatical.driver.DriverProperties.USER_PROP;

public final class DriverPropertiesFactory {

    public static DriverProperties create(DriverType type, String host, int port, String database, String user, String pass, String schema) {

        DriverProperties prototype;
        switch (type) {
            case POSTGRES -> prototype = PostgresDriver.PROTOTYPE;
            case MY_SQL -> prototype = MySqlDriver.PROTOTYPE;
            default -> throw new UnsupportedOperationException("Switch-case is not implemented for type " + type);
        }
        DriverProperties clone = prototype.clone();
        String urlTemplate;
        switch (type) {
            case POSTGRES -> urlTemplate = PostgresDriver.URL_TEMPLATE;
            case MY_SQL -> urlTemplate = MySqlDriver.URL_TEMPLATE;
            default -> throw new UnsupportedOperationException("Switch-case is not implemented for type " + type);
        }

        clone.properties.setProperty(URL_PROP, urlTemplate.formatted(host, port, database, schema));
        clone.properties.setProperty(USER_PROP, user);
        clone.properties.setProperty(PASSWORD_PROP, pass);
        return clone;
    }
}
