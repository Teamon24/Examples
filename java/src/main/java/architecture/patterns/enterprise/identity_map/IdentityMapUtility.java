package architecture.patterns.enterprise.identity_map;

import java.util.HashMap;
import java.util.Map;

public class IdentityMapUtility {

    private static Map<Long, Person> personMap = new HashMap<>();

    public static void addPerson(Person arg) {
        personMap.put(arg.getId(), arg);
    }

    public static Person getPerson(Long key) {
        return personMap.get(key);
    }

    public static Person getPerson(long key) {
        return getPerson(Long.valueOf(key));
    }
}
