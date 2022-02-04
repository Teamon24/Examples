package dbms.hibernate.lify_cycle;

import com.github.javafaker.Faker;
import dbms.hibernate.HibernateUtils;
import dbms.jpa.ex1.UserEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class Demo {
    public static void main(String[] args) {
        Session session = HibernateUtils
            .getSessionFactory("/META-INF/hibernate-postgresql-example.cfg.xml", UserEntity.class)
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
