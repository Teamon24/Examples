package jpa.hiberbate.programmatical.provider;

import jpa.hiberbate.programmatical.driver.DriverProperties;

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
