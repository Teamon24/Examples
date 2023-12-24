package architecture.patterns.basic.other_source.structural.filter.filter;

import architecture.patterns.basic.other_source.structural.filter.Employee;

public class FilterJunior extends StringIgnoreCaseFilter {
    public FilterJunior() {
        super(Employee::getPosition, "Junior");
    }
}