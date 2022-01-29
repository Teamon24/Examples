package dbms.hibernate.lify_cycle;

import com.github.javafaker.Faker;
import dbms.hibernate.HibernateUtils;
import dbms.jpa.ex1.UserEntity;
import dbms.jpa.ex1.programmatical.provider.ProviderProperties;
import dbms.jpa.ex1.programmatical.provider.ProviderPropertiesBuilder;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;

import java.util.Arrays;

public class Demo {
    public static void main(String[] args) {
        Session session = HibernateUtils
            .getSessionFactory("/META-INF/hibernate-postgresql.cfg.xml", UserEntity.class)
            .getCurrentSession();
        Transaction transaction = session.getTransaction();
        transaction.begin();

        UserEntity newUserEntity = new UserEntity();
        newUserEntity.setFullName(new UserEntity.Name("John", "Johnson"));
        newUserEntity.setPassword(Faker.instance().internet().password());
        newUserEntity.setEmail(Faker.instance().internet().emailAddress());
        session.persist(newUserEntity);
        transaction.commit();
    }
}
