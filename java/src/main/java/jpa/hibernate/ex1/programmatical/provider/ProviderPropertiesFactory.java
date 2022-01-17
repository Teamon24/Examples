package jpa.hibernate.ex1.programmatical.provider;

import jpa.hibernate.ex1.programmatical.driver.DriverProperties;

/**
 *
 */
public class ProviderPropertiesFactory {

    public static ProviderProperties createProvider(ProviderType type, DriverProperties driverProperties) {
        switch (type) {
            case HIBERNATE -> { return new HibernateProperties(driverProperties); }
            case ECLIPSE_LINK -> { return new EclipseLinkProperties(driverProperties); }
        }
        throw new UnsupportedOperationException("Switch-case is not implemented for type: " + type);
    }
}
