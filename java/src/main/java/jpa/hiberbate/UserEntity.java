package jpa.hiberbate;


import com.google.common.base.MoreObjects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
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
    String id;

    @Column(name = "full_name")
    String fullName;

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
}
