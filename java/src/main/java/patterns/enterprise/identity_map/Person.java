package patterns.enterprise.identity_map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Person {
    private Long id;
    private String firstName;
    private String lastName;
    private String noOfDependents;
}

