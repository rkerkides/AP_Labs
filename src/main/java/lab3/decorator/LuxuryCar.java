package lab3.decorator;

public class LuxuryCar extends Car {

    @Override
    public Double getPrice() {
        return 50000.0;
    }

    @Override
    public String getDescription() {
        return "a shinier, faster card than the basic one";
    }
}
