package patterns.enterprise.unit_of_work;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

public abstract class UnitOfWork<T> {
    private final Map<OperationType, List<T>> operationsContext;
    private final Database<T> studentDatabase;

    protected UnitOfWork(
        Map<OperationType, List<T>> operationsContext, Database<T> studentDatabase
    ) {
        this.operationsContext = operationsContext;
        this.studentDatabase = studentDatabase;
    }

    /**
     * Any register new operation occurring on UnitOfWork is only going to be performed on commit.
     */
    public void registerNew(T t) {
        System.out.printf("Registering %s for insert in context.%n", t);
        register(t, OperationType.INSERT);
    }

    /**
     * Any register modify operation occurring on UnitOfWork is only going to be performed on commit.
     */
    public void registerModified(T t) {
        System.out.printf("Registering %s for modify in context.%n", t);
        register(t, OperationType.MODIFY);
    }

    /**
     * Any register delete operation occurring on UnitOfWork is only going to be performed on commit.
     */
    public void registerDeleted(T t) {
        System.out.printf("Registering %s for delete in context.%n", t);
        register(t, OperationType.DELETE);
    }

    /**
     * All UnitOfWork operations are batched and executed together on commit only.
     */
    public void commit() {
        if (operationsContext == null || operationsContext.size() == 0) {
            return;
        }

        System.out.println("Commit started");

        if (operationsContext.containsKey(OperationType.INSERT)) {
            commit(OperationType.INSERT, Database::insert);
        }

        if (operationsContext.containsKey(OperationType.MODIFY)) {
            commit(OperationType.MODIFY, Database::modify);
        }

        if (operationsContext.containsKey(OperationType.DELETE)) {
            commit(OperationType.DELETE, Database::delete);
        }

        System.out.println("Commit finished.");
    }

    private void register(T t, OperationType operation) {
        List<T> tsToOperate = operationsContext.computeIfAbsent(operation, ignored -> new ArrayList<>());
        tsToOperate.add(t);
        System.out.printf("Context for '%s': %s%n", operation, operationsContext.get(operation));
    }

    private void commit(OperationType operationType, BiConsumer<Database<T>, T> operation) {
        List<T> ts = operationsContext.get(operationType);
        for (T t : ts) {
            operation.accept(studentDatabase, t);
        }
    }

    enum OperationType {
        INSERT, DELETE, MODIFY
    }

}