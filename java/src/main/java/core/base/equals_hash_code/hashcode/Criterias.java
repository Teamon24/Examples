package core.base.equals_hash_code.hashcode;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>#hashcode() возвращает целочисленное представление объекта.
 * <p>Расчет этого представление должно происходить в соответствии с определением уникальности класса, поэтому при переопределении #equals(Object), мы также должны переопределить #hashcode().
 * <ul>
 * <strong>Три критерия в определении hash code:</strong>
 * <li> hash code меняется, если указанное в #equals(Object) свойство объекта меняется</li>
 * <li> equals-сосгласованность: X.equals(Y) => X.hashCode() == Y.hashCode().</li>
 * <li> X.hashCode() != Y.hashCode() => X.equals(Y) != true.</li>
 * <li> коллизия: X.equals(Y) != true, то может быть X.hashCode() == Y.hashCode().</li>
 * </ul>
 */
public class Criterias {
    public static void main(String[] args) {
        Map<Team,String> leaders = new HashMap<>();

        String newYork = "New York";
        String development = "development";

        Team myTeam = new Team(newYork, development);
        Team key = new Team(newYork, development);

        if (!myTeam.equals(key)) throw new RuntimeException("Equals was implemented incorrectly");

        String myTeamLeader = "Anne";
        leaders.put(myTeam, myTeamLeader);
        leaders.put(new Team("Boston", development), "Brian");
        leaders.put(new Team("Boston", "marketing"), "Charlie");

        if (leaders.get(key) == null) {
            throw new RuntimeException("hashcode was not implemented yet");
        };
    }
}

