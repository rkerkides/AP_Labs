package lab2;

public class Cat extends Pet {
    private String favouriteSpot;

    public Cat(String name, int age, String breed, String furColour, String favouriteSpot) {
        super(name, age, furColour, breed);
        this.favouriteSpot = favouriteSpot;
    }

    @Override
    public String toString() {
        return name + " is a " + breed + " and enjoys being touched on " + favouriteSpot;
    }

    public String toString(String s) {
        return name + " is a " + breed + " and enjoys being touched on its " + favouriteSpot + " " + s;
    }
}
