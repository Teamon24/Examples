package dbms.hibernate.identifiers;

/**
 * <p>Derived identifiers are obtained from an entity's association using the @MapsId annotation.
 * <p>First, let's create a UserProfile entity that derives its id from a one-to-one association with the User entity:
 *
 * <pre>{@code
 * @Entity
 * public class UserProfile {
 *     @Id
 *     private long profileId;
 *
 *     @OneToOne
 *     @MapsId
 *     private User user;
 * }
 * }</pre>
 * <p>Next, let's verify that a UserProfile instance has the same id as its associated User instance:
 * <pre>{@code @Test
 * public void whenSaveDerivedIdEntity_thenOk() {
 *     User user = new User();
 *     session.save(user);
 *
 *     UserProfile profile = new UserProfile();
 *     profile.setUser(user);
 *     session.save(profile);
 *
 *     assertThat(profile.getProfileId()).isEqualTo(user.getUserId());
 * }
 * }</pre>
 */
public interface Derived {
}
