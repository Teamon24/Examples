package patterns.others.structural.filter.filter;

import patterns.others.structural.filter.Employee;

public class FilterJunior extends StringIgnoreCaseFilter {
    public FilterJunior() {
        super(Employee::getPosition, "Junior");
    }
}