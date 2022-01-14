package jpa.hiberbate.programmatical;

import jpa.hiberbate.UserEntity;
import jpa.hiberbate.programmatical.driver.DriverPropertiesFactory;
import jpa.hiberbate.programmatical.driver.DriverProperties;
import jpa.hiberbate.programmatical.driver.DriverType;
import jpa.hiberbate.programmatical.persistence_unit.AbstractPersistenceUnitObject;
import jpa.hiberbate.programmatical.persistence_unit.PersistenceUnitFactory;
import jpa.hiberbate.programmatical.provider.ProviderPropertiesFactory;
import jpa.hiberbate.programmatical.provider.ProviderProperties;
import jpa.hiberbate.programmatical.provider.ProviderType;
import org.hibernate.jpa.HibernatePersistenceProvider;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.util.List;

public class Demo {
    public static void main(String[] args) {
        DriverProperties driverProperties = DriverPropertiesFactory.createDriver(
            DriverType.POSTGRES, "localhost", 5432, "selectel", "selectel", "selectel", "examples"
        );
        ProviderProperties providerProperties = ProviderPropertiesFactory.createProvider(ProviderType.HIBERNATE, driverProperties);
        AbstractPersistenceUnitObject persistenceUnitInfo = PersistenceUnitFactory.createPersistenceInfo(providerProperties);
        EntityManagerFactory factory = createEntityManager(persistenceUnitInfo);
        EntityManager entityManager = factory.createEntityManager();


        Query findAllQuery = entityManager.createQuery("from " + UserEntity.class.getName());
        List<UserEntity> resultList = findAllQuery.getResultList();
        resultList.forEach(System.out::println);

        entityManager.getTransaction().begin();

        resultList.forEach(it -> {
                it.setFullName("John Wick");
                entityManager.merge(it);
            });

        entityManager.getTransaction().commit();

        String newUserId = "new-user-uuid-Av23ad23";
        UserEntity newUser = new UserEntity(newUserId, "Monica", "Hk2#sk!b)32k{E0:", "monica@domain.com");
        persistUser(entityManager, newUser);
        removeUser(entityManager, newUser);

    }

    private static void removeUser(EntityManager entityManager, UserEntity user) {
        entityManager.getTransaction().begin();
        entityManager.remove(user);
        String newUserId = user.getId();
        UserEntity userEntity = entityManager.find(UserEntity.class, newUserId);
        if (userEntity == null) {
            System.out.println("User (id='%s') was removed.".formatted(newUserId));
        }
        entityManager.getTransaction().commit();
    }

    private static void persistUser(EntityManager entityManager, UserEntity newUser) {
        entityManager.getTransaction().begin();
        entityManager.persist(newUser);
        entityManager.getTransaction().commit();
    }

    private static EntityManagerFactory createEntityManager(AbstractPersistenceUnitObject persistenceUnitInfo) {
        ProviderType type = persistenceUnitInfo.getType();
        switch (type) {
            case HIBERNATE -> {
                HibernatePersistenceProvider hibernatePersistenceProvider = new HibernatePersistenceProvider();
                return hibernatePersistenceProvider.createContainerEntityManagerFactory(persistenceUnitInfo, persistenceUnitInfo.getProperties());
            }
            case ECLIPSE_LINK -> {
            }
        }
        throw new UnsupportedOperationException("Switch-case is not implemented for type: " + type);
    }

}
