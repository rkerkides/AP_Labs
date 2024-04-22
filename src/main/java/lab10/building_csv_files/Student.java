package lab10.building_csv_files;

import java.util.Random;

class Student {
    int rollNumber;
    String name;
    short age;
    float grade;

    public Student(int rollNumber, String name, short age, float grade) {
        this.rollNumber = rollNumber;
        this.name = name;
        this.age = age;
        this.grade = grade;
    }

    Student[] populateStudents(int numStudents) {
        Random random = new Random();
        Student[] students = new Student[numStudents];
        for (int i = 0; i < numStudents; i++) {
            String name = generateRandomName(random);
            short age = (short) (random.nextInt(4) + 12);  // Ages between 12 and 15
            float grade = random.nextFloat() * 5;  // Grades between 0.0 and 5.0
            students[i] = new Student(i, name, age, grade);
        }
        return students;
    }

    String generateRandomName(Random random) {
        char c = (char) (random.nextInt(26) + 'A');
        int repeats = random.nextInt(5) + 1;  // Name lengths from 1 to 5
        return new String(new char[repeats]).replace('\0', c);
    }

}

