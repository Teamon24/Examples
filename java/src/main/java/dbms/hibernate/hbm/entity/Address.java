package dbms.hibernate.hbm.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Accessors(chain = true)
public class Address extends AbstractIdentifiableObject {

    private String city;
    private String street;
    private String building;
    private Set<Person> persons = new HashSet<>();

    @Override
    public String toString() {
        return "Address{" +
                "city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", building='" + building + '\'' +
                '}';
    }

}