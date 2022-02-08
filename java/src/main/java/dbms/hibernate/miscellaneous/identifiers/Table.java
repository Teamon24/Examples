package dbms.hibernate.miscellaneous.identifiers;

/**
 * <p>The TableGenerator uses an underlying database table that holds segments of identifier generation values.
 * <p>Let's customize the table name using the @TableGenerator annotation:
 * <pre>{@code
 * @Entity
 * public class Department {
 *     @Id
 *     @GeneratedValue(strategy = GenerationType.TABLE,
 *       generator = "table-generator")
 *     @TableGenerator(name = "table-generator",
 *       table = "dep_ids",
 *       pkColumnName = "seq_id",
 *       valueColumnName = "seq_value")
 *     private long depId;
 * }
 * }</pre>
 * <p>In this example, we can see that we can also customize other attributes such as the pkColumnName and valueColumnName.
 * <p>However, the disadvantage of this method is that it doesn't scale well and can negatively affect performance.
 * <p>To sum up, these four generation types will result in similar values being generated but use different database mechanisms.
 */
public interface Table {
}
