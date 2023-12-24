package architecture.patterns.basic.other_source.structural.filter.filter;

import architecture.patterns.basic.other_source.structural.filter.Employee;

public class FilterMale extends StringIgnoreCaseFilter {
    public FilterMale() {
        super(Employee::getGender, "Male");
    }
}