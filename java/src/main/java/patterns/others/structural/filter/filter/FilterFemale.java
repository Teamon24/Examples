package patterns.others.structural.filter.filter;

import patterns.others.structural.filter.Employee;

public class FilterFemale extends StringIgnoreCaseFilter {
    public FilterFemale() {
        super(Employee::getGender, "Female");
    }
}