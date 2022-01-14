package jpa.hiberbate.programmatical.persistence_unit;

import jpa.hiberbate.programmatical.persistence_unit.AbstractPersistenceUnitObject;
import jpa.hiberbate.programmatical.provider.ProviderType;

import java.util.List;
import java.util.Properties;

public class EclipseLinkPersistenceUnitObject extends AbstractPersistenceUnitObject {


    public EclipseLinkPersistenceUnitObject(String persistenceUnitName, List<String> managedClassNames, Properties properties) {
        super(persistenceUnitName, managedClassNames, properties);
    }

    @Override
    public ProviderType getType() {
        return ProviderType.ECLIPSE_LINK;
    }

    @Override
    public String getPersistenceProviderClassName() {
        return "jakarta.persistence.spi.PersistenceProvider";
    }
}
