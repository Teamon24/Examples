package jpa.hiberbate.programmatical.provider;

import com.google.common.collect.ImmutableMap;
import jpa.hiberbate.programmatical.driver.DriverProperties;
import jpa.hiberbate.programmatical.driver.DriverType;

import static org.hibernate.cfg.AvailableSettings.DIALECT;
import static org.hibernate.cfg.AvailableSettings.FORMAT_SQL;
import static org.hibernate.cfg.AvailableSettings.GENERATE_STATISTICS;
import static org.hibernate.cfg.AvailableSettings.HBM2DDL_AUTO;
import static org.hibernate.cfg.AvailableSettings.QUERY_STARTUP_CHECKING;
import static org.hibernate.cfg.AvailableSettings.SHOW_SQL;
import static org.hibernate.cfg.AvailableSettings.STATEMENT_BATCH_SIZE;
import static org.hibernate.cfg.AvailableSettings.USE_QUERY_CACHE;
import static org.hibernate.cfg.AvailableSettings.USE_REFLECTION_OPTIMIZER;
import static org.hibernate.cfg.AvailableSettings.USE_SECOND_LEVEL_CACHE;
import static org.hibernate.cfg.AvailableSettings.USE_STRUCTURED_CACHE;


public class HibernateProperties extends ProviderProperties {

    public HibernateProperties(DriverProperties driverProperties) {
        super(driverProperties);
        DriverType type = driverProperties.getType();
        String value;
        switch (type) {
            case ORACLE -> value = "org.hibernate.dialect.Oracle12cDialect";
            case POSTGRES -> value = "org.hibernate.dialect.PostgreSQL10Dialect";
            case MY_SQL -> value = "org.hibernate.dialect.MySQLDialect";
            default -> throw new UnsupportedOperationException("Switch-case is not implemented for type: " + type);
        };

        super.properties.putAll(
            ImmutableMap.<String, Object>builder()
                .put(DIALECT, value)
                .put(FORMAT_SQL, true)
                .put(SHOW_SQL, true)
                .put(QUERY_STARTUP_CHECKING, false)
                .put(GENERATE_STATISTICS, false)
                .put(USE_REFLECTION_OPTIMIZER, false)
                .put(USE_SECOND_LEVEL_CACHE, false)
                .put(USE_QUERY_CACHE, false)
                .put(USE_STRUCTURED_CACHE, false)
                .put(STATEMENT_BATCH_SIZE, 20)
                .build()
        );
    }

    @Override
    public ProviderType getType() {
        return ProviderType.HIBERNATE;
    }
}
