package patterns.enterprise.unit_of_work;

import java.util.List;
import java.util.Map;

public final class StudentUnitOfWork extends UnitOfWork<Student> {

    /**
     * @param context         set of operations to be perform during commit.
     * @param studentDatabase Database for student records.
     */
    public StudentUnitOfWork(Map<OperationType, List<Student>> context, StudentDatabase studentDatabase) {
        super(context, studentDatabase);
    }
}