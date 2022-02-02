package dbms.hibernate.relashonship.one_to_one;

import dbms.hibernate.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

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
 *    owning side
 *  _____________       inverse side
 * |  users      |     ___________
 * |_____________|    | addresses |
 * | id          |    |___________|
 * | address_id--|----|>id        |
 * | username    |    | street    |
 *                    | city      |
 * </pre>
 *
 * Case 2: Sharing Primary key
 * <pre>
 *  __________      ___________
 * | post     |    | comment   |
 * |__________|    |___________|
 * | id<------|----|-post_id   |
 * | content  |    | content   |
 * </pre>
 *
 */
public interface Mandatory {
    static void main(String[] args) {
        String resourceName = "/META-INF/hibernate-postgresql.cfg.xml";
        SessionFactory sessionFactory = HibernateUtils.getSessionFactory(resourceName,
            Post.class,
            Comment.class,
            User.class,
            Address.class
        );

        Session session = sessionFactory.openSession();

        // Переоткрытие сессии необходимо для обновления Persistence context,
        // иначе результат вывода на экран будет другой
        session.close();
        session = sessionFactory.openSession();
    }
}

@Entity
@Table(name = "users")
class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;
}

@Entity
@Table(name = "address")
class Address {

    @Id
    @Column(name = "user_id")
    private Long id;

    @OneToOne(mappedBy = "address")
    private User user;
}

@Entity
@Table(name = "posts")
class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @OneToOne(mappedBy = "post", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Comment comment;
}

@Entity
@Table(name = "comments")
class Comment {

    @Id
    @Column(name = "post_id")
    private Long postId;

    @OneToOne
    @JoinColumn(name = "post_id")
    @MapsId
    private Post post;
}


