package lab8;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;

public class InputOutput {
    final static String csvFileName = "students.csv";
    final static String serFileName = "students.ser";
    final static String paddedDOS = "students.padded";
    final static String packedDOS = "students.packed";
    final static int MAX_BYTES = 50;

    public static void main(String[] args) {


        Student[] students = new Student[10000];
        for (int i = 0; i < 10000; i++) {
            students[i] = new Student();
            students[i].rollNumber = i;
            students[i].name = "Student" + i;
            students[i].age = (short) (10 + i % 10);
            students[i].grade = (float) (i % 10);
        }

        writeStudentsToCSV(csvFileName, students);

        serializeStudents(serFileName, students);

        //Student[] students = readStudentsFromCSV(csvFileName);

        /*Student[] students = deserializeStudents(serFileName);




        printStudents(students);*/

        // testPaddedByte("johnny lalala");

        saveToDOS(paddedDOS, students);

        printStudentFromDOS(paddedDOS);

        writeToPackedBinary(students, packedDOS);

        readFromPackedBinary(paddedDOS);
    }

    private static void writeToPackedBinary(Student[] students, String fileName) {
        int nextNameStartByte = 0;
        byte[][] allNameBytes = new byte[students.length][];
        for (int index = 0; index < students.length; ++index) {
            Student student = students[index];
            byte[] nameBytes = student.name.getBytes(StandardCharsets.UTF_8);
            student.nameLength = nameBytes.length;
            student.nameOffset = nextNameStartByte;
            nextNameStartByte += nameBytes.length;
            allNameBytes[index] = nameBytes;
        }

        try (
                OutputStream stream = new FileOutputStream(fileName);
                DataOutputStream dos = new DataOutputStream(stream)
        ) {
            // Write the total number of students -- this will make reading easier
            dos.writeInt(students.length);

            // Write each student as a fixed-size record
            for (int index = 0; index < students.length; ++index) {
                Student student = students[index];
                dos.writeInt(student.rollNumber);
                dos.writeShort(student.age);
                dos.writeFloat(student.grade);
                dos.writeInt(student.nameOffset);
                dos.writeInt(student.nameLength);
            }

            // Write all the name bytes packed together at the end
            for (int index = 0; index < allNameBytes.length; ++index) {
                dos.write(allNameBytes[index]);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Student[] readFromPackedBinary(String filename) {
        int numBytesPerStudent = 4 + 2 + 4 + 4 + 4;
        try (RandomAccessFile file = new RandomAccessFile(filename, "r")) {

            // Prepare a suitable size array to hold the students
            int numStudents = file.readInt();
            Student[] students = new Student[numStudents];
            for (int index = 0; index < numStudents; ++index) {
                // Seek the file to the start position of the next student (extra 4 to skip int numStudents)
                file.seek(4 + (long) index * numBytesPerStudent);

                // Read all the 'simple' fields and create student object
                Student s = new Student();
                s.rollNumber = file.readInt();
                s.name = "";
                s.age = file.readShort();
                s.grade = file.readFloat();

                students[index] = s;

                // Read the fields describing where the name is stored
                int nameOffset = file.readInt();
                int nameLength = file.readInt();

                // Seek to the start of the name, read it, and store in the student object
                // We jump past all the fixed-size student records, then further to the required name
                file.seek(4 + (long) numStudents * numBytesPerStudent + nameOffset);
                byte[] nameBytes = new byte[nameLength];
                file.read(nameBytes);
                s.name = new String(nameBytes, StandardCharsets.UTF_8);
            }
            return students;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void saveToDOS(String fileName, Student[] students) {
        try (
                OutputStream stream = new FileOutputStream(fileName);
                DataOutputStream dos = new DataOutputStream(stream)
        ) {
            for (Student student : students) {
                dos.writeInt(student.rollNumber);
                dos.write(toPaddedBytes(student.name));
                dos.writeShort(student.age);
                dos.writeFloat(student.grade);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void printStudentFromDOS(String fileName) {
        System.out.println("Enter the index of a student:");
        int index = new Scanner(System.in).nextInt();
        int numBytesPerStudent = 4 + MAX_BYTES + 2 + 4;
        try (RandomAccessFile file = new RandomAccessFile(fileName, "r")) {
            file.seek((long) index * numBytesPerStudent);
            System.out.println("rollNumber = " + file.readInt());
            byte[] nameBytes = new byte[MAX_BYTES];
            file.read(nameBytes);
            System.out.println("name = " + fromPaddedBytes(nameBytes));
            System.out.println("age = " + file.readShort());
            System.out.println("grade = " + file.readFloat());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] toPaddedBytes(String string) {
        byte[] name = string.getBytes(StandardCharsets.UTF_8);
        byte[] namePadded = new byte[MAX_BYTES];
        System.arraycopy(name, 0, namePadded, 0, name.length);
        return namePadded;
    }

    private static String fromPaddedBytes(byte[] bytes) {
        int length = 0;
        for (; length < bytes.length; ++length) {
            if (bytes[length] == 0)
                break;  // length of string is index of first padding zero
        }
        return new String(bytes, 0, length, StandardCharsets.UTF_8);
    }

    private static void testPaddedByte(String s) {
        System.out.println(fromPaddedBytes(toPaddedBytes(s)));
    }

    public static void printStudents (Student[] students) {
        for (Student s : students) {
            System.out.println(s.rollNumber + "," + s.name + "," + s.age + "," + s.grade + "\n");
        }
    }

    public static void serializeStudents(String fileName, Student[] students) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(students);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Student[] deserializeStudents(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            throw new RuntimeException("File does not exist: " + fileName);
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (Student[]) ois.readObject();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Class not found during deserialization", e);
        }
    }



    public static void writeStudentsToCSV(String fileName, Student[] students) {
        try (FileWriter fileWriter = new FileWriter(fileName);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {

            for (int i = 0; i < students.length; i++) {
                if (students[i] == null) continue; // Skip any null entries
                // Validate data fields if necessary here

                bufferedWriter.write(students[i].rollNumber + "," + students[i].name + ","
                        + students[i].age + "," + students[i].grade);
                if (i < students.length - 1) {
                    bufferedWriter.write("\n"); // Only write newline if not the last record
                }
            }
            bufferedWriter.flush(); // Ensure data is written to file
        } catch (IOException e) {
            e.printStackTrace(); // More graceful error handling; consider logging or other actions
        }
    }

    public static Student[] readStudentsFromCSV(String fileName) {
        // Read students from file and store in array
        try (FileReader fileReader = new FileReader(fileName)) {
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = "";
            ArrayList<Student> students = new ArrayList<>();

            while ((line = bufferedReader.readLine()) != null) {

                String[] values = line.split(",");

                Student student = new Student();

                student.rollNumber = Integer.parseInt(values[0]);
                student.name = values[1];
                student.age = Short.parseShort(values[2]);
                student.grade = Float.parseFloat(values[3]);

                students.add(student);
            }

            Student[] studentArray = new Student[students.size()];
            return students.toArray(studentArray);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
