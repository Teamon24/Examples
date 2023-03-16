package dbms.hibernate.user_type.ex1;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

@Getter
@AllArgsConstructor
public class PhoneNumber {
    private int countryCode;
    private int cityCode;
    private int number;

    public PhoneNumber(PhoneNumber phoneNumber) {
        this.countryCode = phoneNumber.countryCode;
        this.cityCode = phoneNumber.cityCode;
        this.number = phoneNumber.number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PhoneNumber)) return false;
        PhoneNumber that = (PhoneNumber) o;
        return
            getCountryCode() == that.getCountryCode() &&
                getCityCode() == that.getCityCode() &&
                getNumber() == that.getNumber();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCountryCode(), getCityCode(), getNumber());
    }

}
