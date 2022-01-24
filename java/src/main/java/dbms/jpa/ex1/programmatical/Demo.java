package dbms.jpa.ex1.programmatical;

import dbms.jpa.ex1.UserEntity;
import dbms.jpa.ex1.programmatical.driver.DriverType;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class Demo {
    public static void main(String[] args) {
        EntityManager entityManager = new EntityManagerBuilder()
            .type(DriverType.POSTGRES)
            .host("localhost")
            .port(5432)
            .databaseName("selectel")
            .userName("selectel")
            .password("selectel")
            .schema("examples")
            .build();

        Query findAllQuery = entityManager.createQuery("from " + UserEntity.class.getName());
        List<UserEntity> resultList = findAllQuery.getResultList();
        resultList.forEach(System.out::println);

        entityManager.getTransaction().begin();

        resultList.forEach(it -> {
                it.setFullName(new UserEntity.Name("John","Wick"));
                entityManager.merge(it);
            });

        entityManager.getTransaction().commit();

        UserEntity.Name name = new UserEntity.Name("Monica", "Vertuchi");
        UserEntity newUser = new UserEntity("new-user-uuid-Av23ad23", name, "Hk2#sk!b)32k{E0:", getEmail(name));

        persistUser(entityManager, newUser);
        changeAndMerge(entityManager, newUser);
        removeUser(entityManager, resultList.get(resultList.size()/2));
        entityManager.getTransaction().begin();
        entityManager.getTransaction().commit();
    }

    private static String getEmail(UserEntity.Name name) {
        return name.getFirstName().toLowerCase() + "_" + name.getLastName().toLowerCase() + "@domain.com";
    }

    private static void changeAndMerge(EntityManager entityManager, UserEntity user) {
        String userId = user.getId();
        UserEntity foundUserEntity = entityManager.find(UserEntity.class, userId);
        UserEntity.Name fullName = new UserEntity.Name("Golde", "Brown");
        foundUserEntity.setFullName(fullName);
        entityManager.merge(foundUserEntity);
        UserEntity mergedUserEntity = entityManager.find(UserEntity.class, userId);
        try {
            if (!mergedUserEntity.getFullName().equals(foundUserEntity.getFullName())) {
                throw new RuntimeException("After merging, names are different: '%s' and '%s'");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void removeUser(EntityManager entityManager, UserEntity user) {
        entityManager.remove(user);
        String userId = user.getId();
        UserEntity userEntity = entityManager.find(UserEntity.class, userId);
        if (userEntity == null) {
            System.out.printf("User (id='%s') was removed.%n", userId);
        }
    }

    private static void persistUser(EntityManager entityManager, UserEntity newUser) {
        entityManager.persist(newUser);
    }

}
