package dbms.hibernate.identifiers;

/**
 * <p>Let's say we don't want to use any of the out-of-the-box strategies.
 * In order to do that, we can define our custom generator by implementing the IdentifierGenerator interface.
 * <p>We'll create a generator that builds identifiers containing a String prefix and a number:
 *
 * <pre>{@code
 * public class MyGenerator
 *   implements IdentifierGenerator, Configurable {
 *
 *     private String prefix;
 *
 *     @Override
 *     public Serializable generate(
 *       SharedSessionContractImplementor session, Object obj)
 *       throws HibernateException {
 *
 *         String query = String.format("select %s from %s",
 *             session.getEntityPersister(obj.getClass().getName(), obj)
 *               .getIdentifierPropertyName(),
 *             obj.getClass().getSimpleName());
 *
 *         Stream ids = session.createQuery(query).stream();
 *
 *         Long max = ids.map(o -> o.replace(prefix + "-", ""))
 *           .mapToLong(Long::parseLong)
 *           .max()
 *           .orElse(0L);
 *
 *         return prefix + "-" + (max + 1);
 *     }
 *
 *     @Override
 *     public void configure(Type type, Properties properties,
 *       ServiceRegistry serviceRegistry) throws MappingException {
 *         prefix = properties.getProperty("prefix");
 *     }
 * }
 * }</pre>
 * <p>In this example, we override the generate() method from the IdentifierGenerator interface.
 * <p>First, we want to find the highest number from the existing primary keys of the form prefix-XX. Then we add 1 to the maximum number found and append the prefix property to get the newly generated id value.
 * <p>Our class also implements the Configurable interface so that we can set the prefix property value in the configure() method.
 * <p>Next, let's add this custom generator to an entity.
 * <p>For this, we can use the @GenericGenerator annotation with a strategy parameter that contains the full class name of our generator class:
 * <pre>{@code
 * @Entity
 * public class Product {
 *     @Id
 *     @GeneratedValue(generator = "prod-generator")
 *     @GenericGenerator(name = "prod-generator",
 *       parameters = @Parameter(name = "prefix", value = "prod"),
 *       strategy = "com.baeldung.hibernate.pojo.generator.MyGenerator")
 *     private String prodId;
 * }}</pre>
 * <p>Also, notice we've set the prefix parameter to “prod”.
 * <p>Let's see a quick JUnit test for a clearer understanding of the id values generated:
 * <pre>{@code
 * @Test
 * public void whenSaveCustomGeneratedId_thenOk() {
 *     Product product = new Product();
 *     session.save(product);
 *     Product product2 = new Product();
 *     session.save(product2);
 *     assertThat(product2.getProdId()).isEqualTo("prod-2");
 * }}</pre>
 * <p>Here the first value generated using the “prod” prefix was “prod-1”, followed by “prod-2”.
 */
public interface Custom {
}

