package patterns.basic.refactoring.guru.behavioral.memento.ex1;

import org.apache.commons.lang3.tuple.Pair;
import patterns.basic.refactoring.guru.behavioral.memento.ex1.command.Command;

import java.util.ArrayList;
import java.util.List;

public class History {
    private List<Pair<Command, Memento>> snapshots = new ArrayList<>();
    private int current = 0;

    public void push(Command c, Memento m) {
        if (current != snapshots.size() && current > 0) {
            snapshots = snapshots.subList(0, current - 1);
        }
        snapshots.add(Pair.of(c, m));
        current = snapshots.size();
    }

    public boolean undo() {
        Pair<Command, Memento> pair = getUndo();
        if (pair == null) {
            return false;
        }
        Command command = pair.getLeft();
        Memento memento = pair.getRight();
        System.out.println("Undoing: " + command.getName());
        memento.restore();
        return true;
    }

    public boolean redo() {
        Pair<Command, Memento> snapshot = getRedo();
        if (snapshot == null) {
            return false;
        }
        Command command = snapshot.getKey();
        System.out.println("Redoing: " + command.getName());
        Memento memento = snapshot.getRight();
        memento.restore();
        command.execute();
        return true;
    }

    private Pair<Command, Memento> getUndo() {
        if (current == 0) {
            return null;
        }
        current--;
        return snapshots.get(current);
    }

    private Pair<Command, Memento> getRedo() {
        if (current == snapshots.size()) {
            return null;
        }
        current++;
        return snapshots.get(current - 1);
    }
}