package lab2;

public class Dog extends Pet {
    private String favouriteToy;

    public Dog(String name, int age, String furColour, String breed, String favouriteToy) {
        super(name, age, furColour, breed);
        this.favouriteToy = favouriteToy;
    }

    public void giveTreat() {
        System.out.println(name + " says thanks for the treat!");
    }

    @Override
    public String toString() {
        return name + " is a " + breed + " and enjoys playing with " + favouriteToy;
    }

    public String toString(String s) {
        return name + " is a " + breed + " and enjoys playing with " + favouriteToy + " " + s;
    }
}
