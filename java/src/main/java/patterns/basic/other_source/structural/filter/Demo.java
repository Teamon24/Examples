package patterns.basic.other_source.structural.filter;

import patterns.basic.other_source.structural.filter.filter.AndFilter;
import patterns.basic.other_source.structural.filter.filter.Filter;
import patterns.basic.other_source.structural.filter.filter.OrFilter;
import patterns.basic.other_source.structural.filter.filter.FilterFemale;
import patterns.basic.other_source.structural.filter.filter.FilterJunior;
import patterns.basic.other_source.structural.filter.filter.FilterMale;
import patterns.basic.other_source.structural.filter.filter.FilterSenior;

import java.util.ArrayList;
import java.util.List;

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
        
        System.out.println("Male employees: ");
        printEmployeeInfo(maleEmployees.filter(employees));
        
        System.out.println("\nFemale employees: ");
        printEmployeeInfo(femaleEmployees.filter(employees));
        
        System.out.println("\nSenior female employees: ");
        printEmployeeInfo(seniorFemale.filter(employees));
        
        System.out.println("\nJunior or male employees: ");
        printEmployeeInfo(juniorOrMale.filter(employees));
    }
    
    
    //simple method to print out employee info
    public static void printEmployeeInfo(List<Employee> employeeList) {
        for (Employee employee : employeeList) {
            System.out.println("Employee info: | Name: " 
                    + employee.getName() + ", Gender: " 
                    + employee.getGender() + ", Position: " 
                    + employee.getPosition() + " |");
        }
    }
}