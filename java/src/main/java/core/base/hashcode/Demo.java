package core.base.hashcode;

import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * #hashcode() возвращает целочисленное представление объекта.
 * Расчет этого представление должно происходить в соответствии с определением уникальности класса, поэтому при переопределении #equals(Object), мы также должны переопределить #hashcode().
 * Три критерия в определении hash code:
 * - hash code меняется, если указанное в #equals(Object) свойство объекта меняется
 * - equals-сосгласованность: X.equals(Y) == true => X.hashCode() == Y.hashCode().
 * - X.hashCode() != Y.hashCode() => X.equals(Y) == false.
 * - коллизия: X.equals(Y) == false, то может быть X.hashCode() == Y.hashCode().
 */
public class Demo {
    public static void main(String[] args) {
        Map<Team,String> leaders = new HashMap<>();
        Team myTeam = new Team("New York", "development");
        String myTeamLeader = "Anne";
        leaders.put(myTeam, myTeamLeader);
        leaders.put(new Team("Boston", "development"), "Brian");
        leaders.put(new Team("Boston", "marketing"), "Charlie");

        assert leaders.get(new Team("New York", "development")).equals(myTeamLeader);
    }
}

record Team(String city, String department) {
    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (this == o) return true;
        if (!(o instanceof Team team)) return false;

        boolean citiesEqual = this.city == team.city || this.city != null && this.city.equals(team.city);
        boolean departmentsEqual = this.department == team.department || this.department != null && this.department.equals(team.department);

        return citiesEqual && departmentsEqual;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(31, 17)
            .append(this.city)
            .append(this.department)
            .toHashCode();
    }
}

class Human {
    String sex;
}
class Person extends Human {

    private String name;
    private Integer age;

    Person(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o instanceof Person p) {
            boolean namesEqual = this.name == p.name || this.name != null && this.name.equals(p.name);
            boolean sexesEqual = super.sex == p.sex || super.sex != null && super.sex.equals(p.sex);
            boolean agesEqual = this.age == p.age || this.age != null && this.age.equals(p.age);
            return namesEqual && agesEqual && sexesEqual;
        }

        if (o instanceof Human h) {
            boolean sexesEqual = super.sex == h.sex || super.sex != null && super.sex.equals(h.sex);
            return sexesEqual;
        }

        return false;
    }

    public int hashCode() {
        int result = 17;
        int prime = 31;

        result = result * prime + name.hashCode();
        result = result * prime + age.hashCode();
        result = result * prime + sex.hashCode();
        return result;
    }
}
