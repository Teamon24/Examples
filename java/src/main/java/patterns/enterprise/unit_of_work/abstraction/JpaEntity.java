package patterns.enterprise.unit_of_work.abstraction;

public interface JpaEntity<Id extends Comparable<Id>> {
    Id getId();
}
