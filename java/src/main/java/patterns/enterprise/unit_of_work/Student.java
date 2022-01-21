package patterns.enterprise.unit_of_work;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;

@AllArgsConstructor
public final class Student {

    private Integer id;
    private String name;
    private String address;

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("id", id)
            .append("name", name)
            .append("address", address)
            .toString();
    }
}