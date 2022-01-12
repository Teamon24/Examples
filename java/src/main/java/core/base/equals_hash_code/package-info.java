/**
 * When Do We Override equals() and hashCode()?
 * <ul>Generally, we want to override either both of them or neither of them. </ul>
 *
 * <ul>Domain-Driven Design can help us decide circumstances when we should leave them be. For entity classes – for objects having an intrinsic identity – the default implementation often makes sense.</ul>
 *
 * <ul>However, for value objects, we usually prefer equality based on their properties. </ul>
 *
 * We should remember to:
 *
 * <ul><li>Always override hashCode() if we override equals()</li>
 *     <li>Override equals() and hashCode() for value objects</li>
 *     <li>Be aware of the traps of extending classes that have overridden equals() and hashCode()</li>
 *     <li>Consider using an IDE or a third-party library for generating the equals() and hashCode() methods:
 *     IDE, Guava, Apache, Lombok</li>
 *     <li>Consider using EqualsVerifier to test our implementation</li></ul>
 */
package core.base.equals_hash_code;