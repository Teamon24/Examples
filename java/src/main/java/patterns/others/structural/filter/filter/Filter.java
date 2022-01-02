package patterns.others.structural.filter.filter;

import patterns.others.structural.filter.Employee;

import java.util.List;

public interface Filter {
    List<Employee> filter(List<Employee> employeeList);
}
