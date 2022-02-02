package dbms.hibernate.hbm.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class Company extends AbstractIdentifiableObject {
    private String name;
    private Set<Person> persons = new HashSet<>();

    @Override
    public String toString() {
        return "Company{" +
                "name='" + name + '\'' + "}";
    }
}