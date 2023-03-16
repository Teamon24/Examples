package org.home.components.scopes.common;

import java.util.Objects;

public abstract class CommandManager {
    private static int idCounter;

    public final int id;

    public CommandManager() {
        id = idCounter++;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CommandManager)) return false;
        CommandManager that = (CommandManager) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}