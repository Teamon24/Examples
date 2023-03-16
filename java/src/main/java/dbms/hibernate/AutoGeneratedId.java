package dbms.hibernate;

import lombok.Getter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
public class AutoGeneratedId<IdType extends Serializable> {

    @Id
    @Getter
    @GeneratedValue(strategy= GenerationType.AUTO)
    protected IdType id;
}
