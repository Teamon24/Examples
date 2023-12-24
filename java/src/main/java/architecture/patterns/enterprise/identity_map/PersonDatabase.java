package architecture.patterns.enterprise.identity_map;

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
