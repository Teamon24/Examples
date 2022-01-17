package jpa.hibernate.ex1.programmatical.driver;

public class PostgresDriver extends DriverProperties {

    protected static final PostgresDriver PROTOTYPE = new PostgresDriver();
    protected static final String URL_TEMPLATE = "jdbc:postgresql://%s:%s/%s?currentSchema=%s";

    private PostgresDriver() {}

    @Override
    public DriverType getType() {
        return DriverType.POSTGRES;
    }

    @Override
    public String getDriverName() {
        return "org.postgresql.Driver";
    }
}
