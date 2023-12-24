package architecture.patterns.enterprise.unit_of_work;

import lombok.AllArgsConstructor;
import utils.PrintUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

import static architecture.patterns.enterprise.unit_of_work.UnitOfWork.OperationType.DELETE;
import static architecture.patterns.enterprise.unit_of_work.UnitOfWork.OperationType.INSERT;
import static architecture.patterns.enterprise.unit_of_work.UnitOfWork.OperationType.MODIFY;

abstract class IdentityMap<Id extends Comparable<Id>, T extends JpaEntity<Id>> extends HashMap<Id, T> {}

@AllArgsConstructor
public abstract class UnitOfWork<Id extends Comparable<Id>, T extends JpaEntity<Id>> {

    private interface Operation<Id extends Comparable<Id>, T extends JpaEntity<Id>> extends BiConsumer<Database<Id, T>, T> {}
    private final Map<OperationType, List<T>> operationsContext;
    private final Database<Id, T> database;
    private final IdentityMap<Id, T> identityMap;

    private final Map<OperationType, Operation<Id, T>> operations = new HashMap<>(){{
        put(INSERT, Database::insert);
        put(DELETE, Database::delete);
        put(MODIFY, Database::modify);
    }};

    /**
     * Any register new operation occurring on UnitOfWork is only going to be performed on commit.
     */
    public void registerNew(T newT) {
        identityMap.put(newT.getId(), newT);
        register(newT, INSERT, "Registering %s for insert in context.%n");
    }

    public void registerNew(Collection<T> newTs) {
        newTs.forEach(this::registerNew);
    }

    /**
     * Any register modify operation occurring on UnitOfWork is only going to be performed on commit.
     */
    public void registerModified(T modified) {
        throwIfNotExisted(modified, "Entity (id = '%s') wasn't found hence can't be modified");
        identityMap.put(modified.getId(), modified);
        register(modified, MODIFY, "Registering %s for modify in context.%n");
    }

    public void registerModified(Collection<T> modified) {
        for (T t : modified) {
            registerModified(t);
        }
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
        register(toDelete, DELETE, "Registering %s for delete in context.%n");
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
        if (operationsContext == null || operationsContext.size() == 0) { return; }

        System.out.println("Commit started");
        commitIfContains(INSERT);
        commitIfContains(MODIFY);
        commitIfContains(DELETE);
        System.out.println("Commit finished.");
    }

    private void commitIfContains(OperationType operationType) {
        if (operationsContext.containsKey(operationType)) {
            commit(operationType, operations.get(operationType));
        }
    }

    private void register(T t, OperationType operation, String messageTemplate) {
        PrintUtils.printfln(messageTemplate, t);
        operationsContext
            .computeIfAbsent(operation, ignored -> new ArrayList<>())
            .add(t);

        PrintUtils.printfln("Context for '%s': %s", operation, operationsContext.get(operation));
    }

    private void commit(OperationType operationType, Operation<Id, T> operation) {
        operationsContext.get(operationType).forEach(t -> operation.accept(database, t));
    }

    public enum OperationType {
        INSERT, DELETE, MODIFY
    }

}