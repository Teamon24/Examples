package org.home.components.scopes.common;

import org.home.model.SomeEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SomeServiceImpl implements SomeService<SomeEntity> {

    private SomeRepositoryImpl someRepositoryImpl;

    public SomeServiceImpl(SomeRepositoryImpl someRepositoryImpl) {
        this.someRepositoryImpl = someRepositoryImpl;
    }

    @Override
    public List<SomeEntity> list() {
        return someRepositoryImpl.findAll();
    }

    @Override
    public SomeEntity create(SomeEntity someEntity) {
        return null;
    }

    @Override
    public Optional<SomeEntity> get(int id) {
        return Optional.empty();
    }

    @Override
    public void update(SomeEntity someEntity, int id) {

    }

    @Override
    public void delete(int id) {

    }
}
