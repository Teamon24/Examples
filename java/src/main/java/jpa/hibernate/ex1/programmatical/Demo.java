package jpa.hibernate.ex1.programmatical;

import jpa.hibernate.ex1.UserEntity;
import jpa.hibernate.ex1.programmatical.driver.DriverProperties;
import jpa.hibernate.ex1.programmatical.driver.DriverPropertiesFactory;
import jpa.hibernate.ex1.programmatical.driver.DriverType;
import jpa.hibernate.ex1.programmatical.persistence_unit.AbstractPersistenceUnitObject;
import jpa.hibernate.ex1.programmatical.provider.ProviderProperties;
import jpa.hibernate.ex1.programmatical.provider.ProviderType;
import org.hibernate.SessionFactory;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

import static jpa.hibernate.ex1.programmatical.EntityManagers.createEntityManager;
import static jpa.hibernate.ex1.programmatical.persistence_unit.PersistenceUnitFactory.createPersistenceInfo;
import static jpa.hibernate.ex1.programmatical.provider.ProviderPropertiesFactory.createProvider;

public class Demo {
    public static void main(String[] args) {
        DriverProperties driverProperties = DriverPropertiesFactory.create(
            DriverType.POSTGRES, "localhost", 5432, "selectel", "selectel", "selectel", "examples"
        );

        ProviderProperties providerProperties = createProvider(ProviderType.HIBERNATE, driverProperties);
        List<String> managedClassNames = List.of(UserEntity.class.getSimpleName());

        AbstractPersistenceUnitObject persistenceUnitInfo = createPersistenceInfo(providerProperties, managedClassNames);
        EntityManager entityManager = createEntityManager(persistenceUnitInfo);

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

}
