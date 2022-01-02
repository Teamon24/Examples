package patterns.others.structural.filter.filter;

import patterns.others.structural.filter.Employee;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

public abstract class FilterImpl<T> implements Filter {

    private final Function<Employee, T> fieldExtractor;
    private final T criteriaValue;
    private final BiFunction<T, T, Boolean> equals;

    public FilterImpl(final Function<Employee, T> fieldExtractor,
                      final T criteriaValue,
                      final BiFunction<T, T, Boolean> equals)
    {
        this.fieldExtractor = fieldExtractor;
        this.criteriaValue = criteriaValue;
        this.equals = equals;
    }

    public List<Employee> filter(List<Employee> employeeList) {
        List<Employee> femaleEmployees = new ArrayList<>();

        for(Employee employee : employeeList) {
            Boolean filtered = criteria(employee);
            if(filtered) {
                femaleEmployees.add(employee);
            }
        }
        return femaleEmployees;
    }

    public Boolean criteria(final Employee employee) {
        T employeeField = this.fieldExtractor.apply(employee);
        Boolean filtered = this.equals.apply(employeeField, criteriaValue);
        return filtered;
    }

    public Function<Employee, T> getFieldExtractor() {
        return fieldExtractor;
    }

    public T getCriteriaValue() {
        return criteriaValue;
    }

    public BiFunction<T, T, Boolean> getEquals() {
        return equals;
    }
}