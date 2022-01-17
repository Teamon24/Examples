package jpa.hibernate.entity.mapping;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.TableGenerator;

@Entity
public class Department {
    @Id
    @GeneratedValue(
        strategy = GenerationType.TABLE,
        generator = "table-generator")
    @TableGenerator(
        name = "table-generator",
        table = "dep_ids",
        pkColumnName = "seq_id",
        valueColumnName = "seq_value")
    private long id;
}