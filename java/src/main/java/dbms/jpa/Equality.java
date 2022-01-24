package dbms.jpa;

/**
 *
 * ---------------------------------------------------------------------------------------------------------------------
 * <p><strong>2.1. Collections</strong></p>
 * ---------------------------------------------------------------------------------------------------------------------
 * Java collections group objects together. The grouping logic uses a special value known as a hash code to determine the group for an object.
 *
 * If the value returned by the hashCode() method is the same for all entities, this could result in undesired behavior. Let's say our entity object has a primary key defined as id, but we define our hashCode() method as:
 * <pre>{@code
 * @Override
 * public int hashCode() {
 *     return 12345;
 * }}</pre>
 *
 * Collections will not be able to distinguish between different objects when comparing them because they will all share the same hash code. Luckily, resolving this is as easy as using a unique key when generating a hash code. For example, we can define the hashCode() method using our id:
 * <pre>{@code
 * @Override
 * public int hashCode() {
 *     return id * 12345;
 * }}</pre>
 * In this case, we used the id of our entity to define the hash code. Now, collections can compare, sort, and store our entities.
 * ---------------------------------------------------------------------------------------------------------------------
 * <p><strong>2.2. Transient Entities</strong></p>
 * ---------------------------------------------------------------------------------------------------------------------
 * Newly created JPA entity objects that have no association with a persistence context are considered to be in the transient state. These objects usually do not have their @Id members populated. Therefore, if equals() or hashCode() use the id in their calculations, this means all transient objects will be equal because their ids will all be null. There are not many cases where this is desirable.
 *
 * ---------------------------------------------------------------------------------------------------------------------
 * <p><strong>2.3. Subclasses</strong></p>
 * ---------------------------------------------------------------------------------------------------------------------
 * Subclasses are also a concern when defining equality. It's common to compare classes in the equals() method. Therefore, including the getClass() method will help to filter out subclasses when comparing objects for equality.
 *
 * Let's define an equals() method that will only work if the objects are of the same class and have the same id:
 *
 * <pre>{@code
 * @Override
 * public boolean equals(Object o) {
 *     if (o == null || this.getClass() != o.getClass()) {
 *         return false;
 *     }
 *     return o.id.equals(this.id);
 * }
 * }</pre>
 *
 * ---------------------------------------------------------------------------------------------------------------------
 * <p><strong>3. Defining Equality</strong></p>
 * ---------------------------------------------------------------------------------------------------------------------
 * Given these considerations, we have a few choices when handling equality. Accordingly, the approach we take depends on the specifics of how we plan to use our objects. Let's look at our options.
 *
 * ---------------------------------------------------------------------------------------------------------------------
 * <p><strong>3.1. No Overrides</strong></p>
 * ---------------------------------------------------------------------------------------------------------------------
 * By default, Java provides the equals() and hashCode() methods by virtue of all objects descending from the Object class. Therefore, the easiest thing we can do is nothing. Unfortunately, this means that when comparing objects, in order to be considered equal, they have to be the same instances and not two separate instances representing the same object.
 *
 * ---------------------------------------------------------------------------------------------------------------------
 * <p><strong>3.2. Using a Database Key</strong></p>
 * ---------------------------------------------------------------------------------------------------------------------
 * In most cases, we're dealing with JPA entities that are stored in a database. Normally, these entities have a primary key that is a unique value. Therefore, any instances of this entity that have the same primary key value are equal. So, we can override equals() as we did above for subclasses and also override hashCode() using only the primary key in both.
 *
 * ---------------------------------------------------------------------------------------------------------------------
 * <p><strong>3.3. Using a Business Key</strong></p>
 * ---------------------------------------------------------------------------------------------------------------------
 * Alternatively, we can use a business key to compare JPA entities. In this case, the object's key is comprised of members of the entity other than the primary key. This key should make the JPA entity unique. Using a business key gives us the same desired outcome when comparing entities without the need for primary or database-generated keys.
 *
 * Let's say we know that an email address is always going to be unique, even if it isn't the @Id field. We can include the email field in hashCode() and equals() methods:
 *
 * <pre>{@code
 * public class EqualByBusinessKey {
 *
 *     private String email;
 *
 *     @Override
 *     public int hashCode() {
 *         return java.util.Objects.hashCode(email);
 *     }
 *
 *     @Override
 *     public boolean equals(Object obj) {
 *         if (this == obj) {
 *             return true;
 *         }
 *         if (obj == null) {
 *             return false;
 *         }
 *         if (obj instanceof EqualByBusinessKey) {
 *             if (((EqualByBusinessKey) obj).getEmail().equals(getEmail())) {
 *                 return true;
 *             }
 *         }
 *
 *         return false;
 *     }
 * }
 * }</pre>
 */
public interface Equality {
}
