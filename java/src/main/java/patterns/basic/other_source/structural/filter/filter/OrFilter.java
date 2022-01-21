package patterns.basic.other_source.structural.filter.filter;

import lombok.AllArgsConstructor;
import lombok.Generated;
import patterns.basic.other_source.structural.filter.Employee;

import java.util.List;

@AllArgsConstructor
public final class OrFilter implements Filter {

    private Filter firstFilter;
    private Filter secondFilter;

    @Override
    public List<Employee> filter(List<Employee> employeeList) {
        List<Employee> firstFilterEmployees = firstFilter.filter(employeeList);
        List<Employee> secondFilterEmployees = secondFilter.filter(employeeList);

        for (Employee employee : secondFilterEmployees) {
            addIfNotContained(employee, firstFilterEmployees);
        }
        return firstFilterEmployees;
    }

    private void addIfNotContained(final Employee employee,
                                   final List<Employee> firstCriteriaEmployees)
    {
        if (!firstCriteriaEmployees.contains(employee)) {
            firstCriteriaEmployees.add(employee);
        }
    }
}
