package dbms.hibernate.entity.lify_cycle;

import dbms.jpa.ex1.UserEntity;
import dbms.jpa.ex1.programmatical.provider.ProviderProperties;
import dbms.jpa.ex1.programmatical.provider.ProviderPropertiesBuilder;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;

import javax.persistence.metamodel.EntityType;
import java.util.Set;

public class Demo {
    public static void main(String[] args) {
        Session session = getCurrentSession();
        Transaction transaction = session.getTransaction();
        transaction.begin();

        UserEntity newUserEntity = new UserEntity();
        newUserEntity.setFullName("John Johnson Junior");
        session.persist(newUserEntity);

        Set<EntityType<?>> managedEntities = HibernateLifecycleUtil.getManagedEntities(session);
        System.out.println();
    }

    public static Session getCurrentSession() {
        ProviderProperties providerProperties = new ProviderPropertiesBuilder()
            .port(5432)
            .databaseName("selectel")
            .userName("selectel")
            .password("selectel")
            .schema("examples")
            .build();

        ServiceRegistry serviceRegistry =
            new StandardServiceRegistryBuilder()
                .applySettings(providerProperties.getProperties())
                .build();

        SessionFactory sessionFactory = new MetadataSources(serviceRegistry)
            .buildMetadata()
            .getSessionFactoryBuilder()
            .build();

        return sessionFactory.getCurrentSession();
    }
}
