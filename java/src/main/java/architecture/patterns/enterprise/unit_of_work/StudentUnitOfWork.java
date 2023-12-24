package architecture.patterns.enterprise.unit_of_work;

import java.util.HashMap;

final class StudentIdentityMap extends IdentityMap<Integer, Student> {}

public final class StudentUnitOfWork extends UnitOfWork<Integer, Student> {

    public StudentUnitOfWork(
        StudentDatabase studentDatabase,
        StudentIdentityMap identityMap
    ) {
        super(new HashMap<>(), studentDatabase, identityMap);
    }

    public Student getFirst() { return get(1); }
}
