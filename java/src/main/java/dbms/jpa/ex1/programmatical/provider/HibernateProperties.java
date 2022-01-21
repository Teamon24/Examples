package dbms.jpa.ex1.programmatical.provider;

import com.google.common.collect.ImmutableMap;
import dbms.jpa.ex1.programmatical.driver.DriverProperties;
import dbms.jpa.ex1.programmatical.driver.DriverType;

import java.util.Properties;

import static org.hibernate.cfg.AvailableSettings.CURRENT_SESSION_CONTEXT_CLASS;
import static org.hibernate.cfg.AvailableSettings.DIALECT;
import static org.hibernate.cfg.AvailableSettings.DRIVER;
import static org.hibernate.cfg.AvailableSettings.FORMAT_SQL;
import static org.hibernate.cfg.AvailableSettings.GENERATE_STATISTICS;
import static org.hibernate.cfg.AvailableSettings.PASS;
import static org.hibernate.cfg.AvailableSettings.QUERY_STARTUP_CHECKING;
import static org.hibernate.cfg.AvailableSettings.SHOW_SQL;
import static org.hibernate.cfg.AvailableSettings.STATEMENT_BATCH_SIZE;
import static org.hibernate.cfg.AvailableSettings.URL;
import static org.hibernate.cfg.AvailableSettings.USER;
import static org.hibernate.cfg.AvailableSettings.USE_QUERY_CACHE;
import static org.hibernate.cfg.AvailableSettings.USE_REFLECTION_OPTIMIZER;
import static org.hibernate.cfg.AvailableSettings.USE_SECOND_LEVEL_CACHE;
import static org.hibernate.cfg.AvailableSettings.USE_STRUCTURED_CACHE;


public class HibernateProperties extends ProviderProperties {

    public HibernateProperties(DriverProperties driverProperties) {
        super(driverProperties);
        DriverType type = driverProperties.getType();
        String dialect;
        switch (type) {
            case ORACLE: dialect = "org.hibernate.dialect.Oracle12cDialect"; break;
            case POSTGRES: dialect = "org.hibernate.dialect.PostgreSQL10Dialect"; break;
            case MY_SQL: dialect = "org.hibernate.dialect.MySQLDialect"; break;
            default: throw new UnsupportedOperationException("Switch-case is not implemented for type: " + type);
        };

        Properties props = driverProperties.getProperties();

        super.properties.putAll(
            ImmutableMap.<String, Object>builder()
                .put(DIALECT, dialect)
                .put(FORMAT_SQL, true)
                .put(SHOW_SQL, true)
                .put(CURRENT_SESSION_CONTEXT_CLASS, "thread")
                .put(QUERY_STARTUP_CHECKING, false)
                .put(GENERATE_STATISTICS, false)
                .put(USE_REFLECTION_OPTIMIZER, false)
                .put(USE_SECOND_LEVEL_CACHE, false)
                .put(USE_QUERY_CACHE, false)
                .put(USE_STRUCTURED_CACHE, false)
                .put(STATEMENT_BATCH_SIZE, 20)
                .put(URL, props.get(DriverProperties.URL_PROP))
                .put(USER, props.get(DriverProperties.USER_PROP))
                .put(PASS, props.get(DriverProperties.PASSWORD_PROP))
                .put(DRIVER, props.get(DriverProperties.DRIVER_PROP))
                .build()
        );
    }

    @Override
    public ProviderType getType() {
        return ProviderType.HIBERNATE;
    }
}
