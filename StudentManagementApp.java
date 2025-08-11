import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

class Student implements Serializable {
    private String name;
    private int rollNumber;
    private String grade;

    public Student(String name, int rollNumber, String grade) {
        this.name = name;
        this.rollNumber = rollNumber;
        this.grade = grade;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRollNumber() {
        return rollNumber;
    }

    public void setRollNumber(int rollNumber) {
        this.rollNumber = rollNumber;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return String.format("Roll No: %d, Name: %s, Grade: %s", rollNumber, name, grade);
    }
}

class StudentManagementSystem {
    private List<Student> students;
    private static final String FILE_NAME = "students.dat";

    public StudentManagementSystem() {
        students = new ArrayList<>();
        loadStudentsFromFile();
    }

    public void addStudent(Student student) {
        students.add(student);
        System.out.println("Student added successfully!");
        saveStudentsToFile();
    }

    public boolean removeStudent(int rollNumber) {
        Student studentToRemove = null;
        for (Student student : students) {
            if (student.getRollNumber() == rollNumber) {
                studentToRemove = student;
                break;
            }
        }
        if (studentToRemove != null) {
            students.remove(studentToRemove);
            System.out.println("Student removed successfully!");
            saveStudentsToFile();
            return true;
        } else {
            System.out.println("Student with roll number " + rollNumber + " not found.");
            return false;
        }
    }
    
    public Student searchStudent(int rollNumber) {
        for (Student student : students) {
            if (student.getRollNumber() == rollNumber) {
                return student;
            }
        }
        return null;
    }

    public void displayAllStudents() {
        if (students.isEmpty()) {
            System.out.println("No students found in the system.");
            return;
        }
        System.out.println("\n--- All Students ---");
        for (Student student : students) {
            System.out.println(student);
        }
        System.out.println("--------------------");
    }

    private void saveStudentsToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(students);
        } catch (IOException e) {
            System.out.println("Error saving students to file: " + e.getMessage());
        }
    }

    private void loadStudentsFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            students = (List<Student>) ois.readObject();
            System.out.println("Loaded " + students.size() + " students from file.");
        } catch (FileNotFoundException e) {
            System.out.println("No existing student data found. Starting with an empty list.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading students from file: " + e.getMessage());
        }
    }
}

public class StudentManagementApp {
    private StudentManagementSystem system;
    private Scanner scanner;

    public StudentManagementApp() {
        system = new StudentManagementSystem();
        scanner = new Scanner(System.in);
    }

    public void start() {
        int choice;
        do {
            displayMenu();
            try {
                choice = scanner.nextInt();
                scanner.nextLine();
                handleUserChoice(choice);
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number from the menu.");
                scanner.nextLine();
                choice = 0;
            }
        } while (choice != 6);
        scanner.close();
    }

    private void displayMenu() {
        System.out.println("\n--- Student Management System ---");
        System.out.println("1. Add a new student");
        System.out.println("2. Remove a student");
        System.out.println("3. Search for a student");
        System.out.println("4. Display all students");
        System.out.println("5. Edit student information");
        System.out.println("6. Exit");
        System.out.print("Enter your choice: ");
    }

    private void handleUserChoice(int choice) {
        switch (choice) {
            case 1:
                addStudent();
                break;
            case 2:
                removeStudent();
                break;
            case 3:
                searchStudent();
                break;
            case 4:
                system.displayAllStudents();
                break;
            case 5:
                editStudent();
                break;
            case 6:
                System.out.println("Exiting application. Goodbye!");
                break;
            default:
                System.out.println("Invalid choice. Please enter a number between 1 and 6.");
        }
    }

    private void addStudent() {
        System.out.print("Enter student name: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("Name cannot be empty.");
            return;
        }

        System.out.print("Enter roll number: ");
        int rollNumber = getIntInput();
        if (rollNumber == -1) return;

        System.out.print("Enter grade: ");
        String grade = scanner.nextLine().trim();
        if (grade.isEmpty()) {
            System.out.println("Grade cannot be empty.");
            return;
        }

        Student newStudent = new Student(name, rollNumber, grade);
        system.addStudent(newStudent);
    }
    
    private void removeStudent() {
        System.out.print("Enter roll number of student to remove: ");
        int rollNumber = getIntInput();
        if (rollNumber != -1) {
            system.removeStudent(rollNumber);
        }
    }
    
    private void searchStudent() {
        System.out.print("Enter roll number of student to search: ");
        int rollNumber = getIntInput();
        if (rollNumber != -1) {
            Student student = system.searchStudent(rollNumber);
            if (student != null) {
                System.out.println("Student found: " + student);
            } else {
                System.out.println("Student with roll number " + rollNumber + " not found.");
            }
        }
    }
    
    private void editStudent() {
        System.out.print("Enter roll number of student to edit: ");
        int rollNumber = getIntInput();
        if (rollNumber == -1) return;

        Student student = system.searchStudent(rollNumber);
        if (student == null) {
            System.out.println("Student with roll number " + rollNumber + " not found.");
            return;
        }
        
        System.out.println("Editing student: " + student);
        
        System.out.print("Enter new name (press Enter to keep current: " + student.getName() + "): ");
        String newName = scanner.nextLine().trim();
        if (!newName.isEmpty()) {
            student.setName(newName);
        }
        
        System.out.print("Enter new grade (press Enter to keep current: " + student.getGrade() + "): ");
        String newGrade = scanner.nextLine().trim();
        if (!newGrade.isEmpty()) {
            student.setGrade(newGrade);
        }
        
        System.out.println("Student information updated successfully!");
    }
    
    private int getIntInput() {
        while (true) {
            try {
                int input = scanner.nextInt();
                scanner.nextLine();
                return input;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine();
                return -1;
            }
        }
    }

    public static void main(String[] args) {
        StudentManagementApp app = new StudentManagementApp();
        app.start();
    }
}
