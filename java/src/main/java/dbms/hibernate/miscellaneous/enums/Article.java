package dbms.hibernate.miscellaneous.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.Transient;
import java.util.stream.Stream;

/**
 * <p>As a rule of thumb, we should always use the AttributeConverter interface and @Converter annotation if we're using JPA 2.1 or later.
 *
 * <p>There are various ways of persisting enum values in a database. There are options in JPA 2.0 and below, as well as a new API available in JPA 2.1 and above.
 *
 * <p>It's worth noting that these aren't the only possibilities to deal with enums in JPA. Some databases, like <a href="https://www.postgresql.org/docs/current/datatype-enum.html">PostgreSQL, provide a dedicated column type to store enum values</a>. However, such solutions are outside the scope of this article.
 */
@Entity
public class Article {

    @Id
    private int id;

    private String title;

    /**
     * <p><strong>ORDINAL</strong>
     *
     * <p>When persisting an Article entity:
     * <pre>{@code
     * Article article = new Article();
     * article.setId(1);
     * article.setTitle("ordinal title");
     * article.setStatus(Status.OPEN);
     * }</pre>
     * <p>JPA will trigger the following SQL statement:
     * <pre>{@code
     * insert
     * into
     *     Article
     *     (status, title, id)
     * values
     *     (?, ?, ?)
     *
     * binding parameter [1] as [INTEGER] - [0]
     * binding parameter [2] as [VARCHAR] - [ordinal title]
     * binding parameter [3] as [INTEGER] - [1]
     * }</pre>
     *
     * <p>A problem with this kind of mapping arises when we need to modify our enum. If we add a new value in the middle or rearrange the enum's order, we'll break the existing data model.
     *
     * <p>Such issues might be hard to catch, as well as problematic to fix, as we would have to update all the database records.
     */
    @Enumerated(EnumType.ORDINAL)
    private Status status;

    /**
     *
     * <p><strong>STRING</strong>
     * <p>When persisting an Article entity:
     * <pre>{@code
     * Article article = new Article();
     * article.setId(2);
     * article.setTitle("string title");
     * article.setType(Type.EXTERNAL);}</pre>
     * <p>JPA will execute the following SQL statement:
     * <pre>{@code
     * insert
     * into
     *     Article
     *     (status, title, type, id)
     * values
     *     (?, ?, ?, ?)
     *
     * binding parameter [1] as [INTEGER] - [null]
     * binding parameter [2] as [VARCHAR] - [string title]
     * binding parameter [3] as [VARCHAR] - [EXTERNAL]
     * binding parameter [4] as [INTEGER] - [2]
     * }</pre>
     * <p>With @Enumerated(EnumType.STRING), we can safely add new enum values or change our enum's order. However, renaming an enum value will still break the database data.
     * <p>Additionally, even though this data representation is far more readable compared to the @Enumerated(EnumType.ORDINAL) option, it also consumes a lot more space than necessary. This might turn out to be a significant issue when we need to deal with a high volume of data.
     */
    @Enumerated(EnumType.STRING)
    private Type type;

    @Basic
    private int priorityValue;

    @Transient
    private Priority priority;

    /**
     * <p><strong>@PostLoad</strong> and <strong>@PrePersist</strong></p>
     * <p>Another option we have to deal with persisting enums in a database is to use standard JPA callback methods. We can map our enums back and forth in the @PostLoad and @PrePersist events.
     *
     * <p>The idea is to have two attributes in an entity. The first one is mapped to a database value, and the second one is a @Transient field that holds a real enum value. The transient attribute is then used by the business logic code.
     *
     * <p>...
     */
    @PostLoad
    void fillTransient() {
        if (priorityValue > 0) {
            this.priority = Priority.of(priorityValue);
        }
    }

    /**
     * ...
     *
     * <p>When persisting an Article entity:
     *
     * <pre>{@code
     * Article article = new Article();
     * article.setId(3);
     * article.setTitle("callback title");
     * article.setPriority(Priority.HIGH);
     * }</pre>
     *
     * <p>JPA will trigger the following SQL query:
     *
     * <pre>{@code
     * insert
     * into
     *     Article
     *     (priorityValue, status, title, type, id)
     * values
     *     (?, ?, ?, ?, ?)
     *
     * binding parameter [1] as [INTEGER] - [300]
     * binding parameter [2] as [INTEGER] - [null]
     * binding parameter [3] as [VARCHAR] - [callback title]
     * binding parameter [4] as [VARCHAR] - [null]
     * binding parameter [5] as [INTEGER] - [3]}</pre>
     * <p>Even though this option gives us more flexibility in choosing the database value's representation compared to previously described solutions, it's not ideal. It just doesn't feel right to have two attributes representing a single enum in the entity. Additionally, if we use this type of mapping, we aren't able to use enum's value in JPQL queries.
     */
    @PrePersist
    void fillPersistent() {
        if (priority != null) {
            this.priorityValue = priority.getPriority();
        }
    }

    /**
     *
     * <p><strong>Converter</strong></p>
     * <p>To overcome the limitations of the solutions shown above, JPA 2.1 release introduced a new standardized API that can be used to convert an entity attribute to a database value and vice versa. All we need to do is to create a new class that implements javax.persistence.AttributeConverter and annotate it with @Converter.
     *
     * <p>We've set the @Converter‘s value of <strong>autoApply</strong> to <i>true</i> so that JPA will automatically apply the conversion logic to all mapped attributes of a Category type. Otherwise, we'd have to <strong>put the @Converter annotation directly</strong> on the entity's field.
     *
     * <p>Let's now persist an Article entity:
     * <pre>{@code
     * Article article = new Article();
     * article.setId(4);
     * article.setTitle("converted title");
     * article.setCategory(Category.MUSIC);
     * }</pre>
     * <p>Then JPA will execute the following SQL statement:
     * <pre>{@code
     * insert
     * into
     *     Article
     *     (category, priorityValue, status, title, type, id)
     * values
     *     (?, ?, ?, ?, ?, ?)
     *
     * Converted value on binding : MUSIC -> M
     * binding parameter [1] as [VARCHAR] - [M]
     * binding parameter [2] as [INTEGER] - [0]
     * binding parameter [3] as [INTEGER] - [null]
     * binding parameter [4] as [VARCHAR] - [converted title]
     * binding parameter [5] as [VARCHAR] - [null]
     * binding parameter [6] as [INTEGER] - [4]
     * }</pre>
     * <p>As we can see, we can simply set our own rules of converting enums to a corresponding database value if we use the AttributeConverter interface. Moreover, we can safely add new enum values or change the existing ones without breaking the already persisted data.
     *
     * <p>The overall solution is simple to implement and addresses all the drawbacks of the @PostLoad and @PrePersist options .
     */
    private Category category;
}

enum Status {
    OPEN, REVIEW, APPROVED, REJECTED;
}

enum Type {
    INTERNAL, EXTERNAL;
}

@AllArgsConstructor
enum Category {
    SPORT("S"), MUSIC("M"), TECHNOLOGY("T");
    @Getter private final String code;
}

@AllArgsConstructor
enum Priority {
    LOW(100), MEDIUM(200), HIGH(300);

    @Getter private final int priority;

    public static Priority of(int priority) {
        return Stream.of(Priority.values())
            .filter(p -> p.getPriority() == priority)
            .findFirst()
            .orElseThrow(IllegalArgumentException::new);
    }
}