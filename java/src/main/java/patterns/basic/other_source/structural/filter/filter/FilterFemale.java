package patterns.basic.other_source.structural.filter.filter;

import patterns.basic.other_source.structural.filter.Employee;

public class FilterFemale extends StringIgnoreCaseFilter {
    public FilterFemale() {
        super(Employee::getGender, "Female");
    }
}