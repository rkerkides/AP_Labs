package lab2;

public class Pet extends AbstractPet {
    protected String furColour;
    protected String breed;

    public Pet(String n, int a) {
        this(n, a, "", "");
    }

    public Pet(String n, int a, String furColour, String breed) {
        super(n, a);
        setName(n);
        setAge(a);

        this.furColour = furColour;
        this.breed = breed;
    }

    public Pet(String n) {
        this(n, 0);
    }

    public String getName() {
        return this.name;
    }

    public int getAge() {
        return this.age;
    }

    public void setName(String n) {
        if (n == null || n.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty or null.");
        }
        this.name = n;
    }


    public void setAge(int a) {
        if (a < 0) {
            throw new IllegalArgumentException("Age cannot be negative.");
        }
        this.age = a;
    }

    @Override
    public String toString() {
        return name + " is my pet and is aged " + age;
    }
}
