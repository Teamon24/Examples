package dbms.hibernate;

import dbms.jpa.ex1.programmatical.provider.ProviderProperties;
import dbms.jpa.ex1.programmatical.provider.ProviderPropertiesBuilder;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;

import java.util.Arrays;

public class HibernateUtils {

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory(String resourceName, Class<?>... entitiesClasses) {
        if(sessionFactory == null) sessionFactory = buildSessionFactory(resourceName, entitiesClasses);
        return sessionFactory;
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


        SessionFactory sessionFactory = metadataSources
            .buildMetadata()
            .getSessionFactoryBuilder()
            .build();


        return sessionFactory;
    }

    private static SessionFactory buildSessionFactory(String resourceName, Class<?> ... entitiesClasses) {

        ServiceRegistry serviceRegistry =
            new StandardServiceRegistryBuilder()
                .configure(resourceName)
                .build();

        MetadataSources metadataSources = getMetadataSources(serviceRegistry, entitiesClasses);

        Metadata metadata = metadataSources.getMetadataBuilder().build();

        return metadata.getSessionFactoryBuilder().build();
    }

    private static MetadataSources getMetadataSources(ServiceRegistry serviceRegistry, Class<?>[] entitiesClasses) {
        MetadataSources metadataSources = new MetadataSources(serviceRegistry);
        Arrays.stream(entitiesClasses).forEach(metadataSources::addAnnotatedClass);
        return metadataSources;
    }
}
