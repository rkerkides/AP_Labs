package lab1;

public class Employee extends Person implements Payable {
    private static int numOfEmployees = 0;
    private final int ID;
    private double salary;
    private String department;

    public Employee(String name, double salary, String department) {
        super(name);
        this.salary = salary;
        this.department = department;
        numOfEmployees++;
        this.ID = numOfEmployees;
    }

    public static int getNumOfIDs() {
        return numOfEmployees;
    }


    @Override
    public double calcPaymentAmount() {
        return salary;
    }

    public String getName() {
        return name;
    }

    public int getID() {
        return ID;
    }

    public double getSalary() {
        return salary;
    }

    public String getDepartment() {
        return department;
    }
}
