package lab9;

class Person {
    String name;
    int age;
    Pet ownedPet;
}

abstract class Pet {
    String name;
    Person owner;
    int age;
    float weight;

    public abstract void makeNoise();
}

class Cat extends Pet {
    String favouriteBed;
    int numMiceEaten;
    public void eat(String food) {
        System.out.println(this.name + " is eating " + food);
    }
    public void sleep() {
        System.out.println(this.name + " is sleeping");
    }
    public void hunt(String prey) {
        System.out.println(this.name + " is hunting " + prey);
    }
    public void purr() {
        System.out.println(this.name + " is purring");
    }
    public void makeNoise() {
        purr();
    }
}

class Dog extends Pet {
    String favouriteBone;
    public void bark() {
        System.out.println(this.name + " is barking");
    }
    public void makeNoise() {
        bark();
    }
}

class Duck extends Pet {
    String favouriteBread;
    float lengthOfBeak;
    public void quack() {
        System.out.println(this.name + " is quacking");
    }
    public void makeNoise() {
        quack();
    }
}
