package org.home.components.scopes.common;

import java.util.List;

public interface SomeRepository<T> {
    List<T> findAll();
}
