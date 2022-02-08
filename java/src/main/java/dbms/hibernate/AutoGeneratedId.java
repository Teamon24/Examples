package dbms.hibernate;

import com.google.common.base.Objects;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AutoGeneratedId)) return false;
        AutoGeneratedId<IdType> that = (AutoGeneratedId<IdType>) o;
        return Objects.equal(getId(), that.getId());
    }
}
