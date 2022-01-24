package patterns.enterprise.unit_of_work.abstraction;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

public abstract class UnitOfWork<Id extends Comparable<Id>, T extends Entity<Id>> {

    private final Map<OperationType, List<T>> operationsContext;
    private final Database<Id, T> database;
    private final IdentityMap<Id, T> identityMap;

    protected UnitOfWork(
        Map<OperationType, List<T>> operationsContext,
        Database<Id, T> database,
        IdentityMap<Id, T> identityMap
    ) {
        this.operationsContext = operationsContext;
        this.database = database;
        this.identityMap = identityMap;
    }

    /**
     * Any register new operation occurring on UnitOfWork is only going to be performed on commit.
     */
    public void registerNew(T newT) {
        identityMap.put(newT.getId(), newT);
        register(newT, OperationType.INSERT, "Registering %s for insert in context.%n");
    }

    /**
     * Any register modify operation occurring on UnitOfWork is only going to be performed on commit.
     */
    public void registerModified(T modified) {
        throwIfNotExisted(modified, "Entity (id = '%s') wasn't found hence can't be modified");
        identityMap.put(modified.getId(), modified);
        register(modified, OperationType.MODIFY, "Registering %s for modify in context.%n");
    }

    private void throwIfNotExisted(T modified, String template) {
        T found = identityMap.get(modified.getId());
        if (found == null) {
            String message = String.format(template, modified.getId());
            throw new RuntimeException(message);
        }
    }

    /**
     * Any register delete operation occurring on UnitOfWork is only going to be performed on commit.
     */
    public void registerDeleted(T toDelete) {
        throwIfNotExisted(toDelete, "Entity (id = '%s') wasn't found hence can't be deleted");
        register(toDelete, OperationType.DELETE, "Registering %s for delete in context.%n");
    }

    public T get(Id id) {
        T t = identityMap.get(id);
        if (t != null) {
            return t;
        } else {
            T foundT = database.find(id);
            identityMap.put(id, foundT);
            return foundT;
        }
    }

    public T getLast() {
        T last = database.findLast();
        T cached = identityMap.putIfAbsent(last.getId(), last);
        return last;
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

    private void register(T t, OperationType operation, String messageTemplate) {
        System.out.printf(messageTemplate, t);
        List<T> tsToOperate = operationsContext.computeIfAbsent(operation, ignored -> new ArrayList<>());
        tsToOperate.add(t);
        System.out.printf("Context for '%s': %s%n", operation, operationsContext.get(operation));
    }

    private void commit(OperationType operationType, BiConsumer<Database<Id, T>, T> operation) {
        List<T> ts = operationsContext.get(operationType);
        for (T t : ts) {
            operation.accept(database, t);
        }
    }

    public enum OperationType {
        INSERT, DELETE, MODIFY
    }

}