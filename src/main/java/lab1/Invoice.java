package lab1;

public class Invoice implements Payable {
    public double value;
    public String description;

    public Invoice(double value, String description) {
        this.value = value;
        this.description = description;
    }

    @Override
    public double calcPaymentAmount() {
        return value;
    }
}
