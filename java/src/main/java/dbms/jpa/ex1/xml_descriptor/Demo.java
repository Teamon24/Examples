package dbms.jpa.ex1.xml_descriptor;

import dbms.jpa.ex1.UserDAO;
import dbms.jpa.ex1.UserEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class Demo {
    public static final String PERSISTENCE_UNIT_NAME = "examples.core.jpa.hibernate.postgres";

    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        EntityManager manager = factory.createEntityManager();

        final UserDAO userDao = new UserDAO(manager);
        List<UserEntity> all = userDao.findAll();
        all.forEach(System.out::println);
    }
}