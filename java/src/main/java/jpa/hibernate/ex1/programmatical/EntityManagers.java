package jpa.hibernate.ex1.programmatical;

import jpa.hibernate.ex1.programmatical.persistence_unit.AbstractPersistenceUnitObject;
import jpa.hibernate.ex1.programmatical.provider.ProviderType;
import org.hibernate.jpa.HibernatePersistenceProvider;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 */
public class EntityManagers {
    static EntityManager createEntityManager(AbstractPersistenceUnitObject persistenceUnitInfo) {
        ProviderType type = persistenceUnitInfo.getType();
        switch (type) {
            case HIBERNATE -> {
                HibernatePersistenceProvider provider = new HibernatePersistenceProvider();
                EntityManagerFactory factory =
                    provider.createContainerEntityManagerFactory(
                        persistenceUnitInfo,
                        persistenceUnitInfo.getProperties()
                    );
                return factory.createEntityManager();
            }
            case ECLIPSE_LINK -> {
            }
        }
        throw new UnsupportedOperationException("Switch-case is not implemented for type: " + type);
    }
}
