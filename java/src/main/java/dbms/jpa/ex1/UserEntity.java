package dbms.jpa.ex1;

import com.google.common.base.Objects;
import dbms.hibernate.mapping.NameAttributeConverter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "user", schema = "examples")
@Setter
@Getter
@AllArgsConstructor
public class UserEntity implements Serializable {
    public UserEntity() { }

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    String id;

    @Convert(converter = NameAttributeConverter.class)
    @Column(name = "full_name")
    Name fullName;

    @Column(name = "password", nullable = false)
    String password;

    @Column(name = "email")
    String email;

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("id", id)
            .append("fullName", fullName)
            .append("password", password)
            .append("email", email)
            .toString();
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static final class Name {
        private String firstName;
        private String lastName;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Name)) return false;
            Name name = (Name) o;
            return Objects.equal(
                        getFirstName(),
                        name.getFirstName())
                && Objects.equal(
                        getLastName(),
                        name.getLastName());
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(
                getFirstName(),
                getLastName()
            );
        }
    }
}
