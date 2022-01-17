package jpa.hibernate.ex1.programmatical.persistence_unit;

import jpa.hibernate.ex1.programmatical.provider.ProviderType;
import lombok.Getter;

import javax.persistence.SharedCacheMode;
import javax.persistence.ValidationMode;
import javax.persistence.spi.ClassTransformer;
import javax.persistence.spi.PersistenceUnitInfo;
import javax.persistence.spi.PersistenceUnitTransactionType;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

@Getter
public abstract class AbstractPersistenceUnitObject implements PersistenceUnitInfo {

    public static String JPA_VERSION = "2.1";

    protected String persistenceUnitName;
    protected PersistenceUnitTransactionType transactionType = PersistenceUnitTransactionType.RESOURCE_LOCAL;
    protected List<String> managedClassNames;
    protected List<String> mappingFileNames = new ArrayList<>();
    protected Properties properties;
    protected DataSource jtaDataSource;
    protected DataSource nonjtaDataSource;
    protected List<ClassTransformer> transformers = new ArrayList<>();

    public abstract ProviderType getType();

    public AbstractPersistenceUnitObject(
        String persistenceUnitName, List<String> managedClassNames, Properties properties) {
        this.persistenceUnitName = persistenceUnitName;
        this.managedClassNames = managedClassNames;
        this.properties = properties;
    }

    @Override
    public DataSource getNonJtaDataSource() {
        return null;
    }

    @Override
    public List<URL> getJarFileUrls() {
        try {
            return Collections.list(this.getClass()
                .getClassLoader()
                .getResources(""));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public URL getPersistenceUnitRootUrl() {
        return null;
    }

    @Override
    public boolean excludeUnlistedClasses() {
        return true;
    }

    @Override
    public SharedCacheMode getSharedCacheMode() {
        return SharedCacheMode.NONE;
    }

    @Override
    public ValidationMode getValidationMode() {
        return ValidationMode.AUTO;
    }

    @Override
    public String getPersistenceXMLSchemaVersion() {
        throw new UnsupportedOperationException("Not implemented yet: \"getPersistenceXMLSchemaVersion\"!");
    }

    @Override
    public ClassLoader getClassLoader() {
        return this.getClass().getClassLoader();
    }

    @Override
    public void addTransformer(ClassTransformer transformer) {
        throw new UnsupportedOperationException("Not implemented yet: \"addTransformer\"!");
    }

    @Override
    public ClassLoader getNewTempClassLoader() {
        return null;
    }
}
