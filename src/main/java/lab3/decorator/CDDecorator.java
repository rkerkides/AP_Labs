package lab3.decorator;

public class CDDecorator extends CarDecorator {
    public CDDecorator(Car decoratedCar) {
        super(decoratedCar);
    }

    public String getDescription() {
        return super.getDescription() + " + CD Player";
    }

    public Double getPrice() {
        return super.getPrice() + 150;
    }
}
