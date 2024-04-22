package lab9;

import java.lang.reflect.Field;

public class ReflectiveIO {
    public static void main(String[] args) {
        Cat obj = new Cat();
        obj.age = 3;
        obj.name = "Honhyhyy";
        obj.favouriteBed = "Floor";
        obj.numMiceEaten = 4;
        obj.owner = new Person();
        obj.owner.name = "Alice";
        obj.owner.age = 28;
        printObject(obj);
    }

    public static void printObject(Object obj) {
        Class<?> objClass = obj.getClass();
        System.out.println("Class: " + objClass.getName());

        while (objClass != null) { // Traverse the class hierarchy
            Field[] fields = objClass.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true); // Access private fields
                try {
                    Object fieldValue = field.get(obj);
                    System.out.println("Field " + field.getName() + " is of type " +
                            field.getType().getSimpleName() + ", and has value " +
                            (fieldValue != null ? fieldValue.toString() : "null"));
                } catch (IllegalAccessException e) {
                    System.out.println("Cannot access field: " + field.getName());
                }
            }
            objClass = objClass.getSuperclass(); // Move to the superclass to handle inherited fields
        }
    }
}
