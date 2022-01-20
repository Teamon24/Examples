package dbms.hibernate.entity.mapping;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class Course {

    @Id
    @GeneratedValue
    private UUID id;
}