package dbms.hibernate.mapping;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;

public interface Embeddable {}

class Company {
    private Integer id;
    private String name;
    private String address;
    private String phone;
    private String contactFirstName;
    private String contactLastName;
    private String contactPhone;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "firstName", column = @Column(name = "contact_first_name")),
        @AttributeOverride(name = "lastName", column = @Column(name = "contact_last_name")),
        @AttributeOverride(name = "phone", column = @Column(name = "contact_phone"))
    })
    private ContactPerson contactPerson;


}

@javax.persistence.Embeddable
class ContactPerson {
    private String firstName;
    private String lastName;
    private String phone;
}