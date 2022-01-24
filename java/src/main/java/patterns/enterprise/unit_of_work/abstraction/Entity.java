package patterns.enterprise.unit_of_work.abstraction;

public interface Entity<Id extends Comparable<Id>> {
    Id getId();
}
