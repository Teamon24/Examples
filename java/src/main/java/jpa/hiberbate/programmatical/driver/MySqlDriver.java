package jpa.hiberbate.programmatical.driver;

public class MySqlDriver extends DriverProperties {

    protected static final MySqlDriver PROTOTYPE = new MySqlDriver();
    protected static final String URL_TEMPLATE = "jdbc:mysql://%s:%s/%s?currentSchema=%s";

    private MySqlDriver() {}

    @Override
    public DriverType getType() {
        return DriverType.MY_SQL;
    }

    @Override
    public String getDriverName() {
        return "com.mysql.jdbc.Driver";
    }
}
