package dbms.hibernate.hbm.entity;

import lombok.Getter;
import lombok.Setter;
import utils.JoinUtils;

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
            ", passport=" + JoinUtils.join(passports) +
            ", primaryAddress=" + address +
            ", workingPlaces=" + JoinUtils.join(companies) +
            '}';
    }

    public void remove(Address address) {
        this.getAddress().getPersons().remove(this);
        this.address = null;
    }
}