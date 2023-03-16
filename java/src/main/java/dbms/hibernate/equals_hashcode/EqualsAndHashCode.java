package dbms.hibernate.equals_hashcode;

/**
 * ---------------------------------------------------------------------------------------------------------------------
 * <p><strong>When and Why you need to Implement equals() and hashCodу()</strong></p>
 * ---------------------------------------------------------------------------------------------------------------------
 * <p>As so often in software development, the correct answer to these questions is: It depends …
 *
 * <p>If you take a look at the JPA specification, you will be surprised to only find 2 explicit and 1 implicit
 * mention of both methods:
 *
 * <p>You need to implement the equals() and hashCode() methods for primary key classes if you map composite primary keys.
 * <p>If you map an association to a Map, your map key needs to implement the equals() and hashCode() methods.
 * So, if use an entity as the key, it needs to provide both methods.
 * <p>You can map one-to-many and many-to-many associations to different sub-types of Collection. If you use a Set,
 * your entities have to have equals() and hashCode() methods.
 * <p>Unfortunately, only the first reference provides a clear indication that you need to implement equals() and hashCode()
 * methods for primary key classes. Otherwise, 2 different instances of your primary key object, that have the same attribute
 * values, would be equal in the database but not in your Java code.
 *
 * <p>That would obviously create a lot of problems, but it doesn’t answer the question if you need to implement these
 * methods for your entity classes. The Object class already provides a default implementation of these methods.
 * Are they good enough or do you need to overwrite them?
 *
 * ---------------------------------------------------------------------------------------------------------------------
 * <p><strong>Object’s equals() and hashCode() are not good enough, if …</strong></p>
 * ---------------------------------------------------------------------------------------------------------------------
 * <p>Java’s default implementation of the equals() and hashCode() methods are based on the object’s identity.
 * That means that no two objects are equal and all of them have a different hash code value.
 *
 * <p>Hibernate makes sure to return the same object if you read the same entity twice within a Session.
 * Due to this, the default equals() and hashCode() implementations are OK as long as an entity stays
 * in the context of one Session. So, in the following example, e1 and e2 are the same objects and the equals()
 * method, therefore, returns true.
 *
 * <pre>{@code
 * MyEntity e1 = em.find(MyEntity.class, id);
 * MyEntity e2 = em.find(MyEntity.class, id);
 * Assert.assertTrue(e1.equals(e2));
 * }</pre>
 * <p>But that changes as soon as you work with multiple Sessions or detach and merge an entity, e.g.,
 * by sending it to or retrieving it from a remote client.
 *
 * <p>In the following example, I detach e1 before I fetch e2. Hibernate then instantiates a new object for e2.
 * Based on Java’s default implementation, e1 and e2 are no longer equal, even so they represent the same database record.
 * <pre>{@code
 *
 * MyEntity e1 = em.find(MyEntity.class, id);
 * em.detach(e1);
 *
 * MyEntity e2 = em.find(MyEntity.class, id);
 *
 * Assert.assertFalse(e1.equals(e2));
 *
 * e1 = em.merge(e1);
 *
 * Assert.assertTrue(e1.equals(e2));
 * Assert.assertTrue(e1 == e2);
 * }</pre>
 * As you can see, Java’s default equals() and hashCode() methods only produce the required result if
 * the Hibernate Session ensures that there is only 1 Java object that represents a specific record in the database table.
 * If you load your entities in multiple Sessions or if you work with detached entities, you need to override these methods.
 * In all org.home.other cases, it’s better to rely on Java’s default implementation.
 *
 * ---------------------------------------------------------------------------------------------------------------------
 * <p><strong>Requirements for equals() and hashCode()</strong></p>
 * ---------------------------------------------------------------------------------------------------------------------
 * Java’s language specification defines strict contracts for the equals() and hashCode() methods.
 * You can find them in the Javadoc of the Object class. If you override these methods, you need to make sure that your
 * implementation fulfills these contracts.
 *
 * <ul>The equals() contract
 * Here is the contract defined for the equals() method:
 * <ul>
 * <li><strong>reflexive</strong>: for any non-null reference value x, x.equals(x) should return true.</li>
 * <li><strong>symmetric</strong>: for any non-null reference values x and y, x.equals(y) should return true
 * if and only if y.equals(x) returns true.</li>
 * <li><strong>transitive</strong>: for any non-null reference values x, y, and z, if x.equals(y)
 * returns true and y.equals(z) returns true, then x.equals(z) should return true.</li>
 * <li><strong>consistent</strong>: for any non-null reference values x and y, multiple invocations of x.equals(y)
 * consistently return true or consistently return false, provided no information used in equals comparisons on the
 * objects is modified.</li>
 * <li>For any non-null reference value x, x.equals(null) should return false.</li>
 * </ul>
 * <p>Source: Javadoc
 * </ul>
 *
 * <p>This is a copy of Java’s equals() contract. So, you are probably already familiar with it and implemented
 * it several times for all kinds of Java classes.
 *
 * <p>But if you implement it for a JPA entity, you need to pay special attention to part 4 of the contract.
 * It requires you to write the equals() method in a way that multiple invocations of it return the same result.
 * This gets also enforced by the Set interface:
 *
 * <ul><ul>Great care must be exercised if mutable objects are used as set elements. The behavior of a set is not
 * specified if the value of an object is changed in a manner that affects equals comparisons while the object is an element in the set.
 * </ul>
 * Source: Javadoc
 * </ul>
 *
 *
 * <p>That is a challenge if you use generated primary keys because the identifying attribute of the object changes
 * when it transitions from lifecycle state transient to managed. But more about that later…
 *
 * <ul>The hashCode() contract
 * The hashCode() contract is a little bit easier to implement:
 *
 * <ul>
 *     <li>Whenever it is invoked on the same object more than once during an execution of a Java application, the hashCode
 * method must consistently return the same integer, provided no information used in equals comparisons on the object
 * is modified. This integer need not remain</li>
 * consistent from one execution of an application to another execution of the same application.
 * <li>If two objects are equal according to the equals(Object) method, then calling the hashCode method on each of the
 * two objects must produce the same integer result.</li>
 * <li>It is not required that if two objects are unequal according to the equals(java.lang.Object) method, then calling
 * the hashCode method on each of the two objects must produce distinct integer results. However, the programmer should
 * be aware that producing distinct integer results for unequal objects may improve the performance of hash tables.</li>
 * </ul>
 * Source: Javadoc
 * </ul>
 *
 * <p>The important part of this contract is that the hash code has to be consistent with the result of the equals() method.
 * As you will see in the following paragraphs, that’s relatively easy to achieve.
 *
 * ---------------------------------------------------------------------------------------------------------------------
 * <p><strong>How to implement equals() and hashCode()</strong></p>
 * ---------------------------------------------------------------------------------------------------------------------
 * <p>An entity object represents a record in a database table. Each of these records is identified by
 * a unique primary key value and some of them also have a unique business key. So, it shouldn’t be a surprise
 * if I tell you that you can use these values in your equals() and hashCode() implementation. The complexity of
 * this implementation depends on the kind of key that’s available for your entity.
 *
 * <p>Independent of the available keys, all equals() and hashCode() implementations need to pass the following tests.
 * <pre>{@code
 *
 * // 2 transient entities need to be NOT equal
 * MyEntity e1 = new MyEntity("1");
 * MyEntity e2 = new MyEntity("2");
 * Assert.assertFalse(e1.equals(e2));
 *
 * // 2 managed entities that represent different records need to be NOT equal
 * e1 = em.find(MyEntity.class, id1);
 * e2 = em.find(MyEntity.class, id2);
 * Assert.assertFalse(e1.equals(e2));
 *
 * // 2 managed entities that represent the same record need to be equal
 * e1 = em.find(MyEntity.class, id1);
 * e2 = em.find(MyEntity.class, id1);
 * Assert.assertTrue(e1.equals(e2));
 *
 * // a detached and a managed entity object that represent the same record need to be equal
 * em.detach(e1);
 * e2 = em.find(MyEntity.class, id1);
 * Assert.assertTrue(e1.equals(e2));
 *
 * // a re-attached and a managed entity object that represent the same record need to be equal
 * e1 = em.merge(e1);
 * Assert.assertTrue(e1.equals(e2));
 * }</pre>
 *
 * ---------------------------------------------------------------------------------------------------------------------
 * <p><strong>Using a Business Key or Natural Key</strong></p>
 * ---------------------------------------------------------------------------------------------------------------------
 * <p>The implementation of your equals() and hashCode() methods is pretty easy if your entity has a mandatory business
 * or natural key. As long as this key is immutable and gets always set during the creation of the entity object,
 * you can base your implementation on it. And because the key identifies the object, you don’t need to
 * include any org.home.other entity attributes in your equals() or hashCode() method.
 *
 * <p>Here you can see an example that provides a equals() and hashCode() implementation based
 * on the String businessKey attribute. I also annotated the businessKey attribute with Hibernate’s @NaturalId annotation.
 * This annotation is optional but I highly recommend to use it with all natural IDs. It enables Hibernate’s support for
 * natural ID columns, which makes it a lot easier to load these entities by their natural identifier.
 *
 * <pre>{@code
 * @Entity
 * public class MyEntity {
 *
 *     @Id
 *     @GeneratedValue(strategy = GenerationType.AUTO)
 *     private Long id;
 *
 *     private LocalDate date;
 *     private String message;
 *
 *     @NaturalId private String businessKey;
 *
 *     private MyEntity() {}
 *     public MyEntity(String businessKey) { this.businessKey = businessKey; }
 *
 *
 *     @Override
 *     public int hashCode() { return Objects.hashCode(businessKey); }
 *
 *     @Override
 *     public boolean equals(Object obj) {
 *         if (this == obj) return true;
 *         if (obj == null) return false;
 *         if (getClass() != obj.getClass()) return false;
 *         MyEntity org.home.other = (MyEntity) obj;
 *         return Objects.equals(businessKey, org.home.other.getBusinessKey());
 *     }
 * }
 * }</pre>
 *
 * <p>Please note that the only public constructor of the MyEntity class requires a value for the businessKey attribute.
 * The no-args constructor is private. <strong>This is a Hibernate-specific implementation that’s not supported by the JPA specification.</strong>
 * It ensures that the businessKey attribute is always set and that the hash code of the object will not change.
 *
 * <p>If you want to implement this in a JPA-compliant way, you need to provide a public or protected, no-args constructor.
 * You then need to accept that the hash code of the object changes when you set the businessKey attribute or you need
 * to use a fixed hash code as I do for generated primary key values. In general, a changed hash code doesn’t create
 * any problems as long as you set the value of the businessKey attribute before you add the entity object to any Set.
 *
 * ---------------------------------------------------------------------------------------------------------------------
 * <p><strong>Using a Business Key with a Parent Reference</strong></p>
 * ---------------------------------------------------------------------------------------------------------------------
 * If you use a business key, it happens quite often that it’s only unique if you combine it with a reference to a parent entity.
 * You then need to fetch the parent association eager, include the hash code of the parent entity in your hash code calculation
 * and also check the equality of the referenced parent objects in your equals() method.
 *
 * <p>Here you can see an example that includes the associated MyParent object in the equals() and hashCode() methods.
 *
 * <pre>{@code
 * @Entity
 * public class MyEntity {
 *
 *     @Id
 *     @GeneratedValue(strategy = GenerationType.AUTO)
 *     private Long id;
 *     private LocalDate date;
 *     private String message;
 *
 *     @NaturalId private String businessKey;
 *
 *     @ManyToOne private MyParent parent;
 *
 *     private MyEntity() {}
 *     public MyEntity(String businessKey) { this.businessKey = businessKey; }
 *
 *     @Override
 *     public int hashCode() { return Objects.hash(parent, businessKey); }
 *
 *     @Override
 *     public boolean equals(Object obj) {
 *         if (this == obj) return true;
 *         if (obj == null) return false;
 *         if (getClass() != obj.getClass()) return false;
 *         MyEntity org.home.other = (MyEntity) obj;
 *         return Objects.equals(parent, org.home.other.getParent())
 *                 && Objects.equals(businessKey, org.home.other.getBusinessKey());
 *     }
 * }
 * }</pre>
 * ---------------------------------------------------------------------------------------------------------------------
 * <p><strong>Using a Programmatically Managed Primary Key</strong></p>
 * ---------------------------------------------------------------------------------------------------------------------
 * <p>If you manage your primary key values programmatically, you can implement your equals() and hashCode() methods
 * in almost the same way as I showed you in the previous example. The only requirement here is that you set
 * the primary key value in the constructor or immediately after you instantiated a new entity object.
 *
 * <pre>{@code
 * @Entity
 * public class MyEntity {
 *
 *     @Id
 *     private Long id;
 *     private LocalDate date;
 *     private String message;
 *
 *     private MyEntity() {}
 *     public MyEntity(Long id) { this.id = id; }
 *
 *     @Override
 *     public int hashCode() { return Objects.hashCode(id); }
 *
 *     @Override
 *     public boolean equals(Object obj) {
 *         if (this == obj) return true;
 *         if (obj == null) return false;
 *         if (getClass() != obj.getClass()) return false;
 *         MyEntity org.home.other = (MyEntity) obj;
 *         return Objects.equals(id, org.home.other.getId());
 *     }
 * }
 * }</pre>
 * ---------------------------------------------------------------------------------------------------------------------
 * <p><strong>Using a Generated Primary Key</strong></p>
 * ---------------------------------------------------------------------------------------------------------------------
 * <p>As I teased earlier, generated primary keys create a problem for the implementation of your equals() and
 * hashCode() methods. That’s because the primary key value gets set when the entity gets persisted.
 * So, your entity object can exist with and without a primary key value.
 *
 * <p>The challenge here is that the 
 * <strong>hash code of your entity isn’t allowed to change after you added the object to a Set.
 * So, you can’t use the primary key to calculate the hash code. You need to return a fixed value that’s the same for all
 * objects of the entity class</strong>. That, of course, negatively affects the performance of very huge Sets and
 * Maps because they put all objects into the same hash bucket. But Hibernate can’t efficiently manage huge associations
 * anyways and you should avoid them in general.
 *
 * <p>Here you can see an implementation that uses the primary key value in the equals() method and returns a fixed
 * value as the hash code.
 *
 * <pre>{@code
 * @Entity
 * public class MyEntity {
 *
 *     @Id
 *     @GeneratedValue(strategy = GenerationType.AUTO)
 *     private Long id;
 *
 *     private LocalDate date;
 *     private String message;
 *
 *     @Override
 *     public int hashCode() { return 13; }
 *
 *     @Override
 *     public boolean equals(Object obj) {
 *         if (this == obj) return true;
 *         if (obj == null) return false;
 *         if (getClass() != obj.getClass()) return false;
 *         MyEntity org.home.other = (MyEntity) obj;
 *         return id != null && id.equals(org.home.other.getId());
 *     }
 * }
 * }</pre>
 * ---------------------------------------------------------------------------------------------------------------------
 * <p><strong>Conclusion</strong></p>
 * ---------------------------------------------------------------------------------------------------------------------
 * <p>The implementation of the equals() and hashCode() methods for Hibernate entities is an often discussed topic
 * that provides an interesting, technical challenge. But as I explained at the beginning of this article,
 * you only need to override Object’s default implementations, if you work with multiple Hibernate Sessions or with detached entities.
 * For all org.home.other applications, the default implementation works perfectly fine.
 * <p>If you decide to provide your own equals() and hashCode() implementations, you need to make sure that your code
 * fulfills the contracts defined by the Java language and that the hash code of your objects doesn’t change when
 * the entity gets persisted. The implementation of these methods, therefore, depends on the different keys available
 * for your entity and how you set their values:
 * <ul>
 * <li>If your entity has a business key or a natural ID, you can use it within your equals() and hashCode() method.</li>
 * <li>If you set your primary key values programmatically, you can use its value in your equals check and when you calculate the hash code.</li>
 * <li>If you tell Hibernate to generate your primary key values, you need to use a fixed hash code, and your equals() method requires explicit handling of null values.</li>
 * </ul>
 */
public class EqualsAndHashCode {
}