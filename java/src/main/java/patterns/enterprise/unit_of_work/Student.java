package patterns.enterprise.unit_of_work;

import org.apache.commons.lang3.builder.ToStringBuilder;

public record Student(Integer id, String name, String address) {
    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("id", id)
            .append("name", name)
            .append("address", address)
            .toString();
    }
}