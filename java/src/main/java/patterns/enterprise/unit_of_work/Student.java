package patterns.enterprise.unit_of_work;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import patterns.enterprise.unit_of_work.abstraction.Entity;

import java.util.StringJoiner;

@AllArgsConstructor
public final class Student implements Entity<Integer> {

    @Getter
    private Integer id;
    private String name;
    @Setter
    private String address;

    @Override
    public String toString() {
        return new StringJoiner(", ", Student.class.getSimpleName() + "[", "]")
            .add("id=" + id)
            .add("name='" + name + "'")
            .add("address='" + address + "'")
            .toString();
    }
}