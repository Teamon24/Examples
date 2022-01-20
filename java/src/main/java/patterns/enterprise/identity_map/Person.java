package patterns.enterprise.identity_map;

import lombok.Getter;

public class Person {
    @Getter
    private Long id;
    private String firstName;
    private String lastName;
    private String noOfDependents;
}

