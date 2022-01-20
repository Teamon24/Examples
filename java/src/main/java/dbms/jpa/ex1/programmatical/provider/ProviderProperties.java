package dbms.jpa.ex1.programmatical.provider;

import dbms.jpa.ex1.programmatical.driver.DriverProperties;
import lombok.Getter;

import java.util.Properties;

public abstract class ProviderProperties {

    @Getter
    protected Properties properties = new Properties();

    public ProviderProperties(DriverProperties driverProperties) {
        this.properties.putAll(driverProperties.getProperties());
    }

    public abstract ProviderType getType();
}
