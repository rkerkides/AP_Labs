package lab10.building_csv_files;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Collectors;

public class StreamIO {
    public static void main(String[] args) {
        Student[] students = new Student(0, "John", (short) 15, 4.0f).populateStudents(10000);
        try {
            writeStudentsToCSV(students, "students_from_streams.csv");
            Student[] restored = new StreamIO().readStudentsFromCSV("students_from_streams.csv");
            System.out.println(areStudentsIdentical(students, restored));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeStudentsToCSV(Student[] students, String filename) throws IOException {
        String content = Arrays.stream(students)
                .map(s -> s.rollNumber + "," + s.name + "," + s.age + "," + s.grade)
                .collect(Collectors.joining("\n"));
        Files.write(Paths.get(filename), content.getBytes());
    }

    Student[] readStudentsFromCSV(String filename) throws IOException {
        return Files.lines(Paths.get(filename))
                .map(line -> {
                    String[] parts = line.split(",");
                    int rollNumber = Integer.parseInt(parts[0]);
                    String name = parts[1];
                    short age = Short.parseShort(parts[2]);
                    float grade = Float.parseFloat(parts[3]);
                    return new Student(rollNumber, name, age, grade);
                })
                .toArray(Student[]::new);
    }

    public static boolean areStudentsIdentical(Student[] original, Student[] restored) {
        if (original.length != restored.length) {
            return false; // Arrays of different length cannot be identical
        }
        for (int i = 0; i < original.length; i++) {
            Student s1 = original[i];
            Student s2 = restored[i];
            if (s1.rollNumber != s2.rollNumber ||
                    !s1.name.equals(s2.name) ||
                    s1.age != s2.age ||
                    s1.grade != s2.grade) {
                return false; // If any attribute does not match, the arrays are not identical
            }
        }
        return true; // All elements matched
    }
}
