package dbms.hibernate.entity.lify_cycle;

import org.hibernate.Session;

import javax.persistence.metamodel.EntityType;
import java.util.Set;

/**
 *
 */
public class HibernateLifecycleUtil {
    public static Set<EntityType<?>> getManagedEntities(Session session) {
        Set<EntityType<?>> entities = session.getMetamodel().getEntities();
        return entities;
    }
}
