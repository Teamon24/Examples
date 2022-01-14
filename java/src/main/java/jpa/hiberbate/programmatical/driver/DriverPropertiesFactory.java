package jpa.hiberbate.programmatical.driver;

public class DriverPropertiesFactory {
    public static DriverProperties createDriver(DriverType type, String host, int port, String database, String user, String pass, String schema) {
        switch (type) {
            case POSTGRES -> {
                return PostgresDriver.create(host, port, database, user, pass, schema);
            }
            default -> throw new UnsupportedOperationException("Switch-case is not implemented for type: " + type);
        }
    }
}
