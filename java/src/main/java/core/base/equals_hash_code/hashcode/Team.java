package core.base.equals_hash_code.hashcode;

import lombok.AllArgsConstructor;

import java.util.Objects;

@AllArgsConstructor
class Team {

    private String city;
    private String department;

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (this == o) return true;
        if (!(o instanceof Team)) return false;
        Team other = (Team) o;

        return
            Objects.equals(this.city, other.city) &&
            Objects.equals(this.department, other.department);
    }

    @Override
    public int hashCode() {
        int result = 17;
        int prime = 31;

        result = prime * result + (city != null ? city.hashCode() : 0);
        result = prime * result + (department != null ? department.hashCode() : 0);
        return result;
    }
}