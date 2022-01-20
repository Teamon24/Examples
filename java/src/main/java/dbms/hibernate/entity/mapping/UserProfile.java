package dbms.hibernate.entity.mapping;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

@Entity
public class UserProfile {

    @Id
    private long profileId;
    
    @OneToOne
    @MapsId
    private User user;
}