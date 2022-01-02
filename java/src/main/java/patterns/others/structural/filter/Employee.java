package patterns.others.structural.filter;

public class Employee {
    private String name;
    private String gender;
    private String position;
    
    public Employee(String name, String gender, String position) {
        this.name = name;
        this.gender = gender;
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public String getPosition() {
        return position;
    }
}