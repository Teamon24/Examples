package dbms.hibernate.miscellaneous.identifiers;

/**
 *
 AUTO Generation
 <p>If we're using the default generation type, the persistence provider will determine values based on the type of the primary key attribute. This type can be numerical or UUID.

 <p>For numeric values, the generation is based on a sequence or table generator, while UUID values will use the UUIDGenerator.

 <p>Let's first map an entity primary key using AUTO generation strategy:
 <pre>{@code
 @Entity
    public class Student {
        @Id
        @GeneratedValue
        private long studentId;
    }
}</pre>

 <p>In this case, the primary key values will be unique at the database level.
 <p>Now we'll look at the UUIDGenerator, which was introduced in Hibernate 5.
 <p>In order to use this feature, we just need to declare an id of type UUID with @GeneratedValue annotation:

 <pre>{@code
@Entity
public class Course {
    @Id
    @GeneratedValue
    private UUID courseId;
}}</pre>
 Hibernate will generate an id of the form “8dd5f315-9788-4d00-87bb-10eed9eff566”.
 */
public interface Auto {
}

