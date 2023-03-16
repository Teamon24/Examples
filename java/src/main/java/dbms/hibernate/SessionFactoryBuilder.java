package dbms.hibernate;

import dbms.jpa.ex1.programmatical.provider.ProviderProperties;
import dbms.jpa.ex1.programmatical.provider.ProviderPropertiesBuilder;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataBuilder;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.BasicType;
import org.hibernate.usertype.CompositeUserType;
import org.hibernate.usertype.UserType;

import java.util.Arrays;

public class SessionFactoryBuilder {
    String resourceName;
    Class<?>[] entitiesClasses = new Class<?>[0];
    BasicType[] basicTypes = new BasicType[0];
    UserType[] userTypes = new UserType[0];
    CompositeUserType[] compositeUserTypes = new CompositeUserType[0];

    public SessionFactoryBuilder resourceName(String resourceName) {
        this.resourceName = resourceName;
        return this;
    }

    public SessionFactoryBuilder entitiesClasses(Class<?>... entitiesClasses) {
        this.entitiesClasses = entitiesClasses;
        return this;
    }

    public SessionFactoryBuilder basicTypes(BasicType... basicType) {
        this.basicTypes = basicType;
        return this;
    }

    public SessionFactoryBuilder userTypes(UserType... userType) {
        this.userTypes = userType;
        return this;
    }

    public SessionFactoryBuilder compositeUserTypes(CompositeUserType... compositeUserType) {
        this.compositeUserTypes = compositeUserType;
        return this;
    }

    public SessionFactory build() {
        return createSessionFactory(resourceName, entitiesClasses, basicTypes, userTypes, compositeUserTypes);
    }

    private static SessionFactory createSessionFactory(
        String resourceName,
        Class<?>[] entitiesClasses,
        BasicType[] basicTypes,
        UserType[] userTypes,
        CompositeUserType[] compositeUserTypes
    ) {

        try {
            StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure(resourceName)
                .build();

            MetadataSources metadataSources = new MetadataSources(registry);
            Arrays.stream(entitiesClasses)
                .forEach(metadataSources::addAnnotatedClass);


            MetadataBuilder metadataBuilder = metadataSources.getMetadataBuilder();

            Arrays.stream(basicTypes).forEach(metadataBuilder::applyBasicType);
            Arrays.stream(userTypes).forEach(metadataBuilder::applyBasicType);
            Arrays.stream(compositeUserTypes).forEach(metadataBuilder::applyBasicType);

            Metadata metadata = metadataBuilder.build();


            return metadata.getSessionFactoryBuilder().build();
        } catch (Exception e) {
            throw e;
        }
    }

    private static SessionFactory getSessionFactory(
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
}
