package lab4;

import java.util.*;

public class TestHashSet {
  public static void main(String[] args) {
    // Create a hash set
    Set<String> set = new HashSet<>();

    // Add strings to the set
    set.add("Monday");
    set.add("Tuesday");
    set.add("Wednesday");
    set.add("Friday");
    set.add("Friday");

    System.out.println(set);

    // Display the elements in the hash set
    for (String s: set) {
      System.out.print(s.toUpperCase() + " ");
    }
    System.out.println();
    
    // Process the elements using a forEach method
    set.forEach(e -> System.out.print(e.toLowerCase() + " "));
    System.out.println();
  }
}