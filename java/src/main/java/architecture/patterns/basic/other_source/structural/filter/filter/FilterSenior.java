package architecture.patterns.basic.other_source.structural.filter.filter;

import architecture.patterns.basic.other_source.structural.filter.Employee;

public class FilterSenior extends StringIgnoreCaseFilter {
    public FilterSenior() {
        super(Employee::getPosition, "Senior");
    }
}