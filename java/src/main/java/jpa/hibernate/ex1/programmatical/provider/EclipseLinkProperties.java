package jpa.hibernate.ex1.programmatical.provider;

import jpa.hibernate.ex1.programmatical.driver.DriverProperties;

/**
 *
 */
public class EclipseLinkProperties extends ProviderProperties {
    public EclipseLinkProperties(DriverProperties driverProperties) {
        super(driverProperties);
    }

    @Override
    public ProviderType getType() {
        return ProviderType.ECLIPSE_LINK;
    }
}
