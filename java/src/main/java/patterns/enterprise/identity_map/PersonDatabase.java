package patterns.enterprise.identity_map;

import static patterns.enterprise.identity_map.IdentityMapUtility.addPerson;

public class PersonDatabase {

    public Person finder(int key) {

        // Check for person object in IdentityMap
        Person person = IdentityMapUtility.getPerson(key);
        if (person == null) {
            // get person object from database

            // add person object to IdentityMap
            IdentityMapUtility.addPerson(person);
        }
        return person;

    }
}
