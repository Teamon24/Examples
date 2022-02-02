package dbms.jpa.ex1.programmatical.driver;

public final class PostgresDriver extends DriverProperties {

    static final PostgresDriver PROTOTYPE = new PostgresDriver();
    static final String URL_TEMPLATE = "jdbc:postgresql://%s:%s/%s?currentSchema=%s";

    private PostgresDriver() {}

    @Override
    public String getUrlTemplate() {
        return URL_TEMPLATE;
    }

    @Override
    public DriverType getType() {
        return DriverType.POSTGRES;
    }

    @Override
    public String getDriverName() {
        return "org.postgresql.Driver";
    }
}
