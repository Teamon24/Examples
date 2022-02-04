package dbms.hibernate;

import dbms.jpa.ex1.programmatical.provider.ProviderProperties;
import dbms.jpa.ex1.programmatical.provider.ProviderPropertiesBuilder;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Arrays;
import java.util.List;

public class HibernateUtils {

    private static StandardServiceRegistry registry;
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory(String resourceName, Class<?>... entitiesClasses) {
        if(sessionFactory == null) createSessionFactory(resourceName, entitiesClasses);
        return sessionFactory;
    }

    private static void createSessionFactory(String resourceName, Class<?> ... entitiesClasses) {

        try {
            registry = new StandardServiceRegistryBuilder()
                .configure(resourceName)
                .build();

            MetadataSources metadataSources = new MetadataSources(registry);
            Arrays.stream(entitiesClasses)
                .forEach(metadataSources::addAnnotatedClass);

            Metadata metadata = metadataSources.getMetadataBuilder().build();

            sessionFactory = metadata.getSessionFactoryBuilder().build();
        } catch (Exception e) {
            e.printStackTrace();
            if (registry != null) {
                StandardServiceRegistryBuilder.destroy(registry);
            }
        }
    }

    public static SessionFactory getSessionFactory(
        int port,
        String database,
        String user, String password, String schema,
        Class<?>... annotatedClasses
    ) {
        ProviderProperties providerProperties = new ProviderPropertiesBuilder()
            .port(port)
            .databaseName(database)
            .userName(user)
            .password(password)
            .schema(schema)
            .build();

        ServiceRegistry serviceRegistry =
            new StandardServiceRegistryBuilder()
                .applySettings(providerProperties.getProperties())
                .build();

        MetadataSources metadataSources = new MetadataSources(serviceRegistry);
        Arrays.stream(annotatedClasses).forEach(metadataSources::addAnnotatedClass);


        return metadataSources
            .buildMetadata()
            .getSessionFactoryBuilder()
            .build();
    }

    public static <T> List<T> findAll(Session session, Class<T> type) {
        CriteriaQuery<T> selectFrom = selectFrom(session, type);
        TypedQuery<T> allQuery = session.createQuery(selectFrom);
        return allQuery.getResultList();
    }

    private static <T> CriteriaQuery<T> selectFrom(Session session, Class<T> type) {
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(type);
        Root<T> from = criteriaQuery.from(type);
        return criteriaQuery.select(from);
    }

    public static <T> Long count(Session session, Class<T> type) {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Long> cr = cb.createQuery(Long.class);

        cr.select(cb.count(cr.from(type)));
        Query<Long> query = session.createQuery(cr);
        List<Long> itemProjected = query.getResultList();
        return itemProjected.get(0);
    }

    public static Session reopen(Session session, SessionFactory sessionFactory) {
        session.close();
        session = sessionFactory.openSession();
        return session;
    }
}
