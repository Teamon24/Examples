package dbms.hibernate.hbm.entity;

import lombok.Getter;
import lombok.Setter;

import java.time.Period;
import java.util.Date;

@Setter
@Getter
public class Passport extends AbstractIdentifiableObject {
    private String series;
    private String no;
    private Date issueDate;
    private Period validity;
    private Person person;

    @Override
    public String toString() {
        return "Passport{" +
            "series='" + series + '\'' +
            ", no='" + no + '\'' +
            ", issueDate=" + issueDate +
            ", validity=" + validity +
            '}';
    }
}