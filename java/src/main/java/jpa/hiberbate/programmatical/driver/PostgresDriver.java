package jpa.hiberbate.programmatical.driver;

public class PostgresDriver extends DriverProperties {

    private static final String POSTGRESQL_DRIVER = "org.postgresql.Driver";
    private static final PostgresDriver PROTOTYPE = new PostgresDriver();
    private static final String URL_TEMPLATE = "jdbc:postgresql://%s:%s/%s?currentSchema=%s";

    private PostgresDriver() {
        super();
        super.properties.setProperty(DRIVER_PROP, POSTGRESQL_DRIVER);
    }

    public static DriverProperties create(String host, int port, String database, String user, String pass, String schema) {
        DriverProperties clone = PROTOTYPE.clone();

        clone.properties.setProperty(URL_PROP, URL_TEMPLATE.formatted(host, port, database, schema));
        clone.properties.setProperty(USER_PROP, user);
        clone.properties.setProperty(PASSWORD_PROP, pass);
        return clone;
    }

    @Override
    public DriverType getType() {
        return DriverType.POSTGRES;
    }
}
