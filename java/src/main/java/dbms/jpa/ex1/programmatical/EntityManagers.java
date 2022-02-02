package dbms.jpa.ex1.programmatical;

import dbms.jpa.ex1.programmatical.driver.DriverProperties;
import dbms.jpa.ex1.programmatical.driver.DriverType;
import dbms.jpa.ex1.programmatical.persistence_unit.AbstractPersistenceUnitObject;
import dbms.jpa.ex1.programmatical.provider.ProviderProperties;
import dbms.jpa.ex1.programmatical.provider.ProviderType;
import dbms.jpa.ex1.programmatical.driver.DriverPropertiesFactory;
import org.hibernate.jpa.HibernatePersistenceProvider;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

import static dbms.jpa.ex1.programmatical.persistence_unit.PersistenceUnitFactory.createPersistenceInfo;
import static dbms.jpa.ex1.programmatical.provider.ProviderPropertiesFactory.createProps;

public class EntityManagers {

    public static EntityManager createEntityManager(
        DriverType type,
        String host,
        int port,
        String databaseName,
        String userName,
        String password,
        String schema,
        List<String> managedClasses
    )
    {
        DriverProperties driverProperties = DriverPropertiesFactory.create(
            type, host, port, databaseName, userName, password, schema
        );

        ProviderProperties providerProperties = createProps(ProviderType.HIBERNATE, driverProperties);

        AbstractPersistenceUnitObject persistenceUnitInfo = createPersistenceInfo(providerProperties, managedClasses);
        return EntityManagers.createEntityManager(persistenceUnitInfo);
    }

    static EntityManager createEntityManager(AbstractPersistenceUnitObject persistenceUnitInfo) {
        ProviderType type = persistenceUnitInfo.getType();
        switch (type) {
            case HIBERNATE: {
                HibernatePersistenceProvider provider = new HibernatePersistenceProvider();
                EntityManagerFactory factory =
                    provider.createContainerEntityManagerFactory(
                        persistenceUnitInfo,
                        persistenceUnitInfo.getProperties()
                    );
                return factory.createEntityManager();
            }
            case ECLIPSE_LINK:
        }
        throw new UnsupportedOperationException("Switch-case is not implemented for type: " + type);
    }
}
