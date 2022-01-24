package patterns.enterprise.unit_of_work.abstraction;

import java.util.HashMap;

public abstract class
IdentityMap<
    Id extends Comparable<Id>,
    T extends Entity<Id>
>
    extends HashMap<Id, T>
{
}
