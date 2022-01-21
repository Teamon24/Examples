package dbms.jpa.ex1.programmatical.driver;

public final class DriverPropertiesFactory {

    public static DriverProperties create(
        DriverType type,
        String host,
        int port,
        String database,
        String user,
        String pass,
        String schema)
    {
        DriverProperties prototype = getPrototype(type);
        DriverProperties clone = prototype.clone();

        String url = String.format(getUrlTemplate(type), host, port, database, schema);

        clone.properties.setProperty(DriverProperties.URL_PROP, url);
        clone.properties.setProperty(DriverProperties.USER_PROP, user);
        clone.properties.setProperty(DriverProperties.PASSWORD_PROP, pass);
        return clone;
    }

    private static DriverProperties getPrototype(DriverType type) {
        DriverProperties prototype;
        switch (type) {
            case POSTGRES: prototype = PostgresDriver.PROTOTYPE; break;
            case MY_SQL: prototype = MySqlDriver.PROTOTYPE; break;
            default: throw new UnsupportedOperationException("Switch-case is not implemented for type " + type);
        }
        return prototype;
    }

    private static String getUrlTemplate(DriverType type) {
        String urlTemplate;
        switch (type) {
            case POSTGRES: urlTemplate = PostgresDriver.URL_TEMPLATE; break;
            case MY_SQL: urlTemplate = MySqlDriver.URL_TEMPLATE; break;
            default: throw new UnsupportedOperationException("Switch-case is not implemented for type " + type);
        }
        return urlTemplate;
    }
}
