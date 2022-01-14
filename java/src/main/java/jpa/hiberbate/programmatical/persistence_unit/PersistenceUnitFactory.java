package jpa.hiberbate.programmatical.persistence_unit;

import jpa.hiberbate.UserEntity;
import jpa.hiberbate.programmatical.provider.ProviderProperties;
import jpa.hiberbate.programmatical.provider.ProviderType;

import java.util.List;

/**
 *
 */
public class PersistenceUnitFactory {
    public static AbstractPersistenceUnitObject createPersistenceInfo(ProviderProperties providerProperties) {
        ProviderType type = providerProperties.getType();
        switch (type) {
            case HIBERNATE -> {
                return new HibernatePersistenceUnitObject(
                    "examples-hibernate-postgresql",
                    List.of(UserEntity.class.getSimpleName()),
                    providerProperties.getProperties());
            }
            case ECLIPSE_LINK -> {
                return new EclipseLinkPersistenceUnitObject(
                    "examples-eclipse-link-oracle",
                    List.of(UserEntity.class.getSimpleName()),
                    providerProperties.getProperties()
                );
            }
        }
        throw new UnsupportedOperationException("Switch-case is not implemented for type: " + type);
    }
}
