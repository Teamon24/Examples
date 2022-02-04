package patterns.enterprise.unit_of_work.abstraction;

import java.util.HashMap;

public abstract class
IdentityMap<
    Id extends Comparable<Id>,
    T extends JpaEntity<Id>
>
    extends HashMap<Id, T>
{
}
