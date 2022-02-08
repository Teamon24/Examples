package dbms.hibernate.miscellaneous.identifiers;

/**
 *
 * <p>To use a sequence-based id, Hibernate provides the SequenceStyleGenerator class.
 * <p>This generator uses sequences if <strong>our database supports them</strong>. It switches to table generation if they aren't supported.
 * <p>In order to customize the sequence name, we can use the <i>@GenericGenerator</i> annotation with SequenceStyleGenerator strategy:
 * <pre>{@code
 * @Entity
 * public class User {
 *     @Id
 *     @GeneratedValue(generator = "user-id-sequence-generator")
 *     @GenericGenerator(
 *       name = "user-id-sequence-generator",
 *       strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
 *       parameters = {
 *         @Parameter(name = "entity_user_id_sequence", value = "db_user_id_sequence"),
 *         @Parameter(name = "initial_value", value = "4"),
 *         @Parameter(name = "increment_size", value = "1")
 *         }
 *     )
 *     private long userId;
 * }
 * }</pre>
 * <p>In this example, we've also set an initial value for the sequence, which means the primary key generation will start at 4.
 * <p>SEQUENCE is the <strong>generation type recommended by the Hibernate</strong> documentation.
 * <p>The generated values are unique per sequence. If we don't specify a sequence name, Hibernate will reuse the same hibernate_sequence for different types.
 */
public interface Sequence {
}
