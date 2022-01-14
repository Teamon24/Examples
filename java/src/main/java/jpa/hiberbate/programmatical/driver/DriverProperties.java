package jpa.hiberbate.programmatical.driver;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Properties;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class DriverProperties implements Cloneable {

    public static final String DRIVER_PROP = "javax.persistence.jdbc.driver";
    public static final String URL_PROP = "javax.persistence.jdbc.url";
    public static final String USER_PROP = "javax.persistence.jdbc.user";
    public static final String PASSWORD_PROP = "javax.persistence.jdbc.password";

    @Getter
    protected Properties properties = new Properties();

    public abstract DriverType getType();

    @Override
    public DriverProperties clone() {
        try {
            DriverProperties clone = (DriverProperties) super.clone();
            clone.properties = (Properties) this.properties.clone();
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
