package patterns.others.structural.filter.filter;

import patterns.others.structural.filter.Employee;

public class FilterMale extends StringIgnoreCaseFilter {
    public FilterMale() {
        super(Employee::getGender, "Male");
    }
}