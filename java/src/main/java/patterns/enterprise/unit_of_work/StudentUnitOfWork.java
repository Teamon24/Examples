package patterns.enterprise.unit_of_work;

import patterns.enterprise.unit_of_work.abstraction.IdentityMap;
import patterns.enterprise.unit_of_work.abstraction.UnitOfWork;

import java.util.List;
import java.util.Map;

public final class StudentUnitOfWork extends UnitOfWork<Integer, Student> {

    /**
     * @param context         set of operations to be perform during commit.
     * @param studentDatabase database for student records.
     * @param identityMap     identity map for cache.
     */
    public StudentUnitOfWork(
        Map<OperationType, List<Student>> context,
        StudentDatabase studentDatabase,
        IdentityMap<Integer, Student> identityMap
    ) {
        super(context, studentDatabase, identityMap);
    }
}