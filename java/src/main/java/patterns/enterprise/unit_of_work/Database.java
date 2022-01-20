package patterns.enterprise.unit_of_work;

public interface Database<T> {
    void insert(T student);
    void modify(T student);
    void delete(T student);
}
