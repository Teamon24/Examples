package jpa.hiberbate.ex1.programmatical.driver;

import jpa.hibernate.ex1.programmatical.driver.DriverProperties;
import jpa.hibernate.ex1.programmatical.driver.DriverPropertiesFactory;
import jpa.hibernate.ex1.programmatical.driver.DriverType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Properties;

import static jpa.hibernate.ex1.programmatical.driver.PostgresDriver.*;

/**
 *
 */
class PostgresDriverTest {

    @Test
    void createTest() {
        DriverProperties driverProperties1 =
            DriverPropertiesFactory.create(
                DriverType.POSTGRES, "host_1", 1111, "db_1", "user_1", "pass_1", "schema_1");

        DriverProperties driverProperties2 =
            DriverPropertiesFactory.create(
                DriverType.POSTGRES, "host_2", 2222, "db_2", "user_2", "pass_2", "schema_2");

        Properties properties1 = driverProperties1.getProperties();
        Properties properties2 = driverProperties2.getProperties();

        Assertions.assertNotEquals(properties1, properties2);

        Object driver1 = properties1.get(DRIVER_PROP);
        Object driver2 = properties2.get(DRIVER_PROP);
        Assertions.assertEquals(driver1, driver2);

        properties1.setProperty(PASSWORD_PROP, "new_pass_1");
        properties2.setProperty(PASSWORD_PROP, "new_pass_2");

        Assertions.assertNotEquals(
            properties1.get(PASSWORD_PROP),
            properties2.get(PASSWORD_PROP)
        );
    }
}