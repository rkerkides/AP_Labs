package lab1;

public class Manager extends Employee {
    private double bonus;

    public Manager(String name, double salary, String department, double bonus) {
        super(name, salary, department);
        this.bonus = bonus;
    }

    public double calcTotalEarnings() {
        return super.getSalary() + this.bonus;
    }

    @Override
    public double calcPaymentAmount() {
        return calcTotalEarnings();
    }
}
