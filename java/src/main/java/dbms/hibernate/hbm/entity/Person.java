package dbms.hibernate.hbm.entity;

import lombok.Getter;
import lombok.Setter;
import utils.CollectionUtils;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class Person extends AbstractIdentifiableObject {
    private String firstName;
    private String lastName;
    private Date birthDate;
    private Set<Passport> passports = new HashSet<>();
    private Address address;
    private Set<Company> companies = new HashSet<>();

    @Override
    public String toString() {
        return "Person{" +
            "firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", birthDate=" + birthDate +
            ", passport=" + CollectionUtils.join(passports) +
            ", primaryAddress=" + address +
            ", workingPlaces=" + CollectionUtils.join(companies) +
            '}';
    }

    public void remove(Address address) {
        this.address.getPersons().remove(this);
        this.address = null;
    }
}