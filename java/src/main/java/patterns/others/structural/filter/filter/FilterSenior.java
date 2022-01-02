package patterns.others.structural.filter.filter;

import patterns.others.structural.filter.Employee;

public class FilterSenior extends StringIgnoreCaseFilter {
    public FilterSenior() {
        super(Employee::getPosition, "Senior");
    }
}