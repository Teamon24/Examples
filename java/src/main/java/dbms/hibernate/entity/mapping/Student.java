package dbms.hibernate.entity.mapping;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import java.util.Date;

@Entity
@Table(name= Student.STUDENT_TABLE)
public class Student {

    protected static final String STUDENT_TABLE = "STUDENT";

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @Column(
        name="STUDENT_NAME",
        length=50,
        nullable=false,
        unique=false
    )
    private String name;

    @Transient
    private Integer age;

    /**
     * However, with JPA 2.2, we also have support for:
     * <ul>
     * <li>java.time.LocalDate</li>
     * <li>java.time.LocalTime</li>
     * <li>java.time.LocalDateTime</li>
     * <li>java.time.OffsetTime</li>
     * <li>java.time.OffsetDateTime</li>
     * </ul>
     */
    @Temporal(TemporalType.DATE)
    private Date birthDate;

    @Enumerated(EnumType.STRING)
    private Gender gender;
}