package core.base.equals_hashcode;

/**
 * When Do We Override equals() and hashCode()?
 * <ul>Generally, we want to override either both of them or neither of them. </ul>
 *
 * <ul>Domain-Driven Design can help us decide circumstances when we should leave them be. For entity classes – for objects having an intrinsic identity – the default implementation often makes sense.</ul>
 *
 * <ul>However, for value objects, we usually prefer equality based on their properties. </ul>
 *
 * <b>We should remember to:</b>
 *
 * <ul><li>Always override hashCode() if we override equals()</li>
 *     <li>Override equals() and hashCode() for value objects</li>
 *     <li>Be aware of the traps of extending classes that have overridden equals() and hashCode()</li>
 *     <li>Consider using an IDE or a third-party library for generating the equals() and hashCode() methods:
 *     <b>IDE</b>, <b>Guava</b>, <b>Apache</b>, <b>Lombok</b></li>
 *     <li>Consider using <b>EqualsVerifier</b> to test our implementation</li></ul>
 */
public interface EqualsAndHashcode {
}
