package lab2;

public class PetTest {
    public static void main(String[] args) {
        Pet[] pets = new Pet[10];
        pets[0] = new Dog("Rex", 5, "brown", "Labrador", "ball");
        pets[1] = new Dog("Buddy", 3, "black", "Poodle", "frisbee");
        pets[2] = new Dog("Max", 7, "white", "Poodle", "bone");
        pets[3] = new Cat("Whiskers", 2, "Siamese", "white", "head");
        pets[4] = new Cat("Mittens", 4, "Persian", "grey", "back");
        pets[5] = new Cat("Fluffy", 1, "Siamese", "white", "tail");
        pets[6] = new Dog("Rover", 6, "black", "Labrador", "stick");
        pets[7] = new Dog("Spot", 4, "brown", "Poodle", "rope");
        pets[8] = new Cat("Snowball", 3, "Persian", "white", "back");
        pets[9] = new Pet("Tiger", 5, "Siamese", "grey");

        Dog dog = new Dog("Rex", 5, "brown", "Labrador", "ball");
        printPets(pets);

        System.out.println(dog.toString("today"));
    }

    public static void printPets(Pet[] pets) {
        for (Pet pet : pets) {
            System.out.println(pet);
        }
    }
}
