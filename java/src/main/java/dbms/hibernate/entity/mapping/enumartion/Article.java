package dbms.hibernate.entity.mapping.enumartion;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.Transient;

@Entity
public class Article {

    @Id
    private int id;

    private String title;

    @Enumerated(EnumType.ORDINAL)
    private Status status;

    @Enumerated(EnumType.STRING)
    private Type type;

    @Basic
    private int priorityValue;

    @Transient
    private Priority priority;

    private Category category;

    @PostLoad
    void fillTransient() {
        if (priorityValue > 0) {
            this.priority = Priority.of(priorityValue);
        }
    }

    @PrePersist
    void fillPersistent() {
        if (priority != null) {
            this.priorityValue = priority.getPriority();
        }
    }
}