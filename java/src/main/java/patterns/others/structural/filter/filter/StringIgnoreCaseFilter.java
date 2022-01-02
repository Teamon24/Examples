package patterns.others.structural.filter.filter;

import patterns.others.structural.filter.Employee;

import java.util.function.Function;

public class StringIgnoreCaseFilter extends FilterImpl<String> {

    public StringIgnoreCaseFilter(final Function<Employee, String> fieldExtractor,
                                  final String criteriaValue)
    {
        super(fieldExtractor, criteriaValue, String::equalsIgnoreCase);
    }
}
