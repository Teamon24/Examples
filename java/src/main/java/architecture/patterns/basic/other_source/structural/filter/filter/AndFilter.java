package architecture.patterns.basic.other_source.structural.filter.filter;

import lombok.AllArgsConstructor;
import architecture.patterns.basic.other_source.structural.filter.Employee;

import java.util.List;

@AllArgsConstructor
public final class AndFilter implements Filter {

    private Filter firstFilter;
    private Filter secondFilter;

    @Override
    public List<Employee> filter(List<Employee> employeeList) {
        return secondFilter.filter(firstFilter.filter(employeeList));
    }
}