package lab9;

import java.lang.reflect.Method;
import java.util.Scanner;

public class Reflection {
    public static void main(String[] args) {
        try {
            // Create a new instance of pets.Cat
            Object cat = Class.forName("lab9.Cat").newInstance();

            Scanner scan = new Scanner(System.in);
            System.out.println("Which method would you like the cat to perform?");

            String methodName = scan.nextLine();

            // Get the class of the object cat
            Class<?> catClass = cat.getClass();

            // List all methods to find a match for the given method name with its parameters
            Method[] methods = catClass.getMethods();
            Method chosenMethod = null;

            for (Method method : methods) {
                if (method.getName().equals(methodName)) {
                    Class<?>[] params = method.getParameterTypes();
                    if (params.length == 0) {
                        chosenMethod = method;
                        break;
                    } else {
                        System.out.println("The method " + methodName + " requires " + params.length + " parameter(s). Please enter them:");
                        Object[] paramValues = new Object[params.length];
                        for (int i = 0; i < params.length; i++) {
                            System.out.println("Enter a value for parameter of type " + params[i].getSimpleName() + ":");
                            String paramInput = scan.nextLine();
                            paramValues[i] = convertStringToType(paramInput, params[i]);
                        }
                        chosenMethod = method;
                        chosenMethod.invoke(cat, paramValues);
                        return;
                    }
                }
            }

            if (chosenMethod != null && chosenMethod.getParameterCount() == 0) {
                chosenMethod.invoke(cat);
                System.out.println("Method " + methodName + " called successfully.");
            } else if (chosenMethod == null) {
                System.out.println("No suitable method found.");
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Cat class not found.");
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    private static Object convertStringToType(String input, Class<?> type) {
        if (type == int.class) {
            return Integer.parseInt(input);
        } else if (type == double.class) {
            return Double.parseDouble(input);
        } else if (type == boolean.class) {
            return Boolean.parseBoolean(input);
        } else if (type == String.class) {
            return input;
        }
        throw new IllegalArgumentException("Unsupported parameter type: " + type);
    }
}
