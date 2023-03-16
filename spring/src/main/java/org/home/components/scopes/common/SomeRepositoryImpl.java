package org.home.components.scopes.common;

import org.home.model.SomeEntity;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class SomeRepositoryImpl implements SomeRepository<SomeEntity> {

    @Override
    public List<SomeEntity> findAll() {
        List<SomeEntity> someEntities = new ArrayList<>();
        SomeEntity someEntity = new SomeEntity(1,
                "Getting Started with Spring Boot 2",
                "Learn how to build a real application using Spring Framework 5 & Spring Boot 2",
                "https://www.danvega.dev/courses");
        someEntities.add(someEntity);
        return someEntities;
    }

}
