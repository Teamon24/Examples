package patterns.others.structural.filter.filter;

import patterns.others.structural.filter.Employee;

import java.util.List;

public record AndFilter(Filter firstFilter,
                        Filter secondFilter) implements Filter {

    @Override
    public List<Employee> filter(List<Employee> employeeList) {
        return secondFilter.filter(firstFilter.filter(employeeList));
    }
}