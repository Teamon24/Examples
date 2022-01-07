package core.base.hashcode;

import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

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

        return
            Objects.equals(this.city, team.city) &&
            Objects.equals(this.department, team.department);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(31, 17)
            .append(this.city)
            .append(this.department)
            .toHashCode();
    }
}