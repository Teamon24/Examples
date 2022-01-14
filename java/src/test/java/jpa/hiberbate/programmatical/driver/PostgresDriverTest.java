package jpa.hiberbate.programmatical.driver;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Properties;

import static jpa.hiberbate.programmatical.driver.PostgresDriver.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 */
class PostgresDriverTest {

    @Test
    void create() {
        DriverProperties driverProperties1 = PostgresDriver.create("host_1", 1111, "db_1", "user_1", "pass_1", "schema_1");
        DriverProperties driverProperties2 = PostgresDriver.create("host_2", 2222, "db_2", "user_2", "pass_2", "schema_2");

        Properties properties1 = driverProperties1.properties;
        Object driver1 = properties1.get(DRIVER_PROP);

        Properties properties2 = driverProperties2.properties;
        Object driver2 = properties2.get(DRIVER_PROP);

        Assertions.assertEquals(driver1, driver2);

        properties1.setProperty(PASSWORD_PROP, "new_pass_1");
        properties2.setProperty(PASSWORD_PROP, "new_pass_2");

        Assertions.assertNotEquals(properties1.get(PASSWORD_PROP), properties2.get(PASSWORD_PROP));
    }
}