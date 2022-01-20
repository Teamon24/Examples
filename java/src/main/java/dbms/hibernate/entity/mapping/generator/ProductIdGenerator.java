package dbms.hibernate.entity.mapping.generator;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.exception.spi.Configurable;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;
import java.util.Properties;
import java.util.stream.Stream;

public class ProductIdGenerator
    implements IdentifierGenerator, Configurable {

    private String prefix;

    @Override
    public Serializable generate(
        SharedSessionContractImplementor session,
        Object obj
    ) throws HibernateException {

        String tableName = obj.getClass().getSimpleName();
        String idName = session.getEntityPersister(obj.getClass().getName(), obj)
            .getIdentifierPropertyName();
        String query = String.format("select %s from %s", idName, tableName);

        Stream<String> ids = session.createQuery(query).stream();

        Long max = ids
            .map(this::deletePrefix)
            .mapToLong(Long::parseLong)
            .max()
            .orElse(0L);

        return prefix + "-" + (max + 1);
    }

    private String deletePrefix(String o) {
        return o.replace(prefix + "-", "");
    }

    @Override
    public void configure(Properties properties) throws HibernateException {
        this.prefix = (String) properties.get("prefix");
    }
}