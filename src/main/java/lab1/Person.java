package lab1;

public abstract class Person {
    protected String name;

    protected Person(String name) {
        this.name = name;
    }
    public void editName(String name) {
        this.name = name;
    }
}
