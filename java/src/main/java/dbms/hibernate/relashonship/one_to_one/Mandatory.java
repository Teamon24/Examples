package dbms.hibernate.relashonship.one_to_one;

import dbms.hibernate.HibernateUtils;
import dbms.hibernate.SessionFactoryBuilder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
import java.util.List;

import static dbms.hibernate.TransactionUtils.commit;
import static dbms.hibernate.TransactionUtils.operate;
import static dbms.hibernate.TransactionUtils.refresh;
import static utils.PrintUtils.printfln;


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
        SessionFactory sessionFactory = new SessionFactoryBuilder()
            .resourceName("/META-INF/hibernate-postgresql-example.cfg.xml")
            .entitiesClasses(Post.class, Comment.class, User.class, Address.class)
            .build();

        Session session = sessionFactory.openSession();

        case1(session);
        case2(session);
    }

    static void case1(Session session) {

        List<?> entities = commit(session, s -> {
            User user = new User(new Address());
            User user_NoAddress = new User();
            Address address_NoUser = new Address();
            return operate(s, Session::save, user, user_NoAddress, address_NoUser);
        });

        refresh(session, entities);

        HibernateUtils
            .findAll(session, Address.class)
            .forEach(address -> printfln("Address id = %s, user id = %s", address.getId(), address.getUserId()));

        HibernateUtils
            .findAll(session, User.class)
            .forEach(user -> printfln("User id = %s, address id = %s", user.getId(), user.getAddressId()));
    }

    static void case2(Session session) {
        List<?> entities = commit(session, s -> {
            Post post = new Post();
            Comment comment = new Comment(post);
            Post post_NoComment = new Post();
            return operate(session, Session::save, post, comment, post_NoComment);
        });

        operate(session, Session::refresh, entities);

        HibernateUtils
            .findAll(session, Post.class)
            .forEach(p -> printfln("Post id = %s, comment id = %s", p.getId(), p.getCommentId()));

        HibernateUtils
            .findAll(session, Comment.class)
            .forEach(c -> printfln("Comment post_id = %s", c.getPostId()));
    }
}

@Getter
@Entity
@Table(name = "users")
@NoArgsConstructor
class User {

    public User(Address address) {
        this.address = address;
    }

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Setter
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;

    public Long getAddressId() {
        return address == null ? null : address.getId();
    }
}

@Entity
@Table(name = "address")
@Getter
@NoArgsConstructor
class Address {

    public Address(User user) {
        this.user = user;
    }

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Setter
    @OneToOne(mappedBy = "address")
    private User user;

    public Long getUserId() {
        return user == null ? null : user.getId();
    }
}

@Entity
@Table(name = "posts")
@Getter
class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @OneToOne(mappedBy = "post", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Comment comment;

    public Long getCommentId() {
        return comment == null ? null : comment.getPostId();
    }
}

@Entity
@Table(name = "comments")
@Getter
@NoArgsConstructor
class Comment {

    public Comment(Post post) {
        this.post = post;
    }

    @Id
    @Column(name = "post_id")
    private Long postId;

    @MapsId
    @OneToOne
    @JoinColumn(name = "post_id")
    private Post post;

    public Long getPostId() {
        return post == null ? null : post.getId();
    }
}


