package dbms.hibernate.relashonship.one_to_one;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;


/**
 * Case 1:
 *
 * <pre>
 *  _____________
 * |  users      |     ___________
 * |_____________|    | addresses |
 * | id          |    |___________|
 * | address_id--|----|>id        |
 * | username    |    | street    |
 *                    | city      |
 * </pre>
 *
 * Case 2:
 * <pre>
 *  __________      ___________
 * | users    |    | addresses |
 * |__________|    |___________|
 * | id<------|----|-user_id   |
 * | username |    | id        |
 * |          |    | street    |
 * |          |    | city      |
 * </pre>
 *
 */
public interface Mandatory {}

@Entity
@Table(name = "users")
class User {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    /**
     * Case 1.
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address1;

    /**
     * Case 2.
     */
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Address address2;
}

@Entity
@Table(name = "address")
class Address {

    @Id
    @Column(name = "user_id")
    private Long id;

    /**
     * Case 1.
     */
    @OneToOne(mappedBy = "address1")
    private User user1;

    /**
     * Case 2.
     */
    @OneToOne
    @JoinColumn(name = "user_id")
    @MapsId
    private User user2;
}


