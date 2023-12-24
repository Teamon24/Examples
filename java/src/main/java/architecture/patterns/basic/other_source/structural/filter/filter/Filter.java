package architecture.patterns.basic.other_source.structural.filter.filter;

import architecture.patterns.basic.other_source.structural.filter.Employee;

import java.util.List;

public interface Filter {
    List<Employee> filter(List<Employee> employeeList);
}
