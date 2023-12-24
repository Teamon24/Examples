package core.base.equals_hashcode.hashcode;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>#hashcode() возвращает целочисленное представление объекта.
 * <p>Расчет этого представление должно происходить в соответствии с определением уникальности класса, поэтому при переопределении #equals(Object), мы также должны переопределить #hashcode().
 * <ul>
 * <strong>Три критерия в определении hash code:</strong>
 * <li> hash code меняется, если указанное в #equals(Object) свойство объекта меняется</li>
 * <li> Объекты равны, значит и hashcode равны (equals-согласованность): X == Y => h(X) == h(Y).</li>
 * <li> Если hashcode не равны, то и объекты не равны: h(X) != h(Y) => X == Y.</li>
 * </ul>
 * <ul>
 * <b>Hashcode-коллизия: </b>X.equals(Y) != true, то может быть h(X) == h(Y).
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

