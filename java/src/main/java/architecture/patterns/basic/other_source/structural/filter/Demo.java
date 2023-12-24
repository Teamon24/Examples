package architecture.patterns.basic.other_source.structural.filter;

import architecture.patterns.basic.other_source.structural.filter.filter.AndFilter;
import architecture.patterns.basic.other_source.structural.filter.filter.FilterFemale;
import architecture.patterns.basic.other_source.structural.filter.filter.FilterMale;
import architecture.patterns.basic.other_source.structural.filter.filter.FilterSenior;
import architecture.patterns.basic.other_source.structural.filter.filter.OrFilter;
import architecture.patterns.basic.other_source.structural.filter.filter.Filter;
import architecture.patterns.basic.other_source.structural.filter.filter.FilterJunior;

import java.util.ArrayList;
import java.util.List;

import static java.lang.System.out;

public class Demo {
    public static void main(String[] args) {
        List<Employee> employees = new ArrayList<>();
        
        //adding employees to the list
        String senior = "Senior";
        String junior = "Junior";

        String male = "Male";
        String female = "Female";

        employees.add(new Employee("David", male, senior));
        employees.add(new Employee("Scott", male, senior));
        employees.add(new Employee("Rhett", male, junior));
        employees.add(new Employee("Andrew", male, junior));
        employees.add(new Employee("Susan", female, senior));
        employees.add(new Employee("Rebecca", female, junior));
        employees.add(new Employee("Mary", female, junior));
        employees.add(new Employee("Juliette", female, senior));
        employees.add(new Employee("Jessica", female, junior));
        employees.add(new Employee("Mike", male, junior));
        employees.add(new Employee("Chris", male, junior));


        //initialization of the different criteria classes
        Filter maleEmployees = new FilterMale();
        Filter femaleEmployees = new FilterFemale();
        Filter seniorEmployees = new FilterSenior();
        Filter juniorEmployees = new FilterJunior();
        //AndCriteria and OrCriteria accept two Criteria as their constructor
        Filter seniorFemale = new AndFilter(seniorEmployees, femaleEmployees);
        Filter juniorOrMale = new OrFilter(juniorEmployees, maleEmployees);
        
        out.println("Male employees: ");
        printEmployeeInfo(maleEmployees.filter(employees));
        
        out.println("\nFemale employees: ");
        printEmployeeInfo(femaleEmployees.filter(employees));
        
        out.println("\nSenior female employees: ");
        printEmployeeInfo(seniorFemale.filter(employees));
        
        out.println("\nJunior or male employees: ");
        printEmployeeInfo(juniorOrMale.filter(employees));
    }
    
    
    //simple method to print out employee info
    public static void printEmployeeInfo(List<Employee> employeeList) {
        for (Employee employee : employeeList) {
            out.println("Employee info: | Name: "
                    + employee.getName() + ", Gender: " 
                    + employee.getGender() + ", Position: " 
                    + employee.getPosition() + " |");
        }
    }
}