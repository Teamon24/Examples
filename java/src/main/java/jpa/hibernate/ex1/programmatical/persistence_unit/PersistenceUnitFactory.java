package jpa.hibernate.ex1.programmatical.persistence_unit;

import jpa.hibernate.ex1.programmatical.provider.ProviderProperties;
import jpa.hibernate.ex1.programmatical.provider.ProviderType;

import java.util.List;

/**
 *
 */
public class PersistenceUnitFactory {

    public static AbstractPersistenceUnitObject createPersistenceInfo(
        ProviderProperties providerProperties,
        List<String> managedClassNames)
    {
        ProviderType type = providerProperties.getType();
        switch (type) {
            case HIBERNATE -> {
                return new HibernatePersistenceUnitObject(
                    "examples-hibernate-postgresql",
                    managedClassNames,
                    providerProperties.getProperties());
            }
            case ECLIPSE_LINK -> {
                return new EclipseLinkPersistenceUnitObject(
                    "examples-eclipse-link-oracle",
                    managedClassNames,
                    providerProperties.getProperties()
                );
            }
        }
        throw new UnsupportedOperationException("Switch-case is not implemented for type: " + type);
    }
}
