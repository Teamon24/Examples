package dbms.jpa.ex1.programmatical.persistence_unit;

import dbms.jpa.ex1.programmatical.provider.ProviderType;
import lombok.Getter;

import java.util.List;
import java.util.Properties;

@Getter
public class HibernatePersistenceUnitObject extends AbstractPersistenceUnitObject {

    public HibernatePersistenceUnitObject(String persistenceUnitName, List<String> managedClassNames, Properties properties) {
        super(persistenceUnitName, managedClassNames, properties);
    }

    @Override
    public ProviderType getType() {
        return ProviderType.HIBERNATE;
    }

    @Override
    public String getPersistenceProviderClassName() {
        return "org.hibernate.jpa.HibernatePersistenceProvider";
    }
}