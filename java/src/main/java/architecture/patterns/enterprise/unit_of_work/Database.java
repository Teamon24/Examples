package architecture.patterns.enterprise.unit_of_work;

import java.util.Collection;
import java.util.Comparator;

public interface Database<Id extends Comparable<Id>, T extends JpaEntity<Id>> {

    T find(Id id);
    void insert(T student);
    void modify(T student);
    void delete(T student);
    Collection<T> findAll();

    default T findLast() {
        return findAll().stream()
            .max(Comparator.comparing(JpaEntity::getId))
            .orElseThrow(() -> new RuntimeException("Database is empty"));
    }
}

interface JpaEntity<Id extends Comparable<Id>> { Id getId(); }
