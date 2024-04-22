package lab3.decorator;

public class AlloyDecorator extends CarDecorator {
    public AlloyDecorator(Car decoratedCar) {
        super(decoratedCar);
    }

    public String getDescription() {
        return super.getDescription() + " + Alloys";
    }

    public Double getPrice() {
        return super.getPrice() + 250;
    }
}
