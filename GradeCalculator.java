import java.util.Scanner;

public class GradeCalculator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter your marks for three subjects (out of 100):");

        System.out.print("Maths: ");
        int mathsMarks = scanner.nextInt();

        System.out.print("Science: ");
        int scienceMarks = scanner.nextInt();

        System.out.print("English: ");
        int englishMarks = scanner.nextInt();
        int totalMarks = mathsMarks + scienceMarks + englishMarks;
        double averagePercentage = (double) totalMarks / 3;
        char grade;
        if (averagePercentage >= 90) {
            grade = 'A';
        } else if (averagePercentage >= 80) {
            grade = 'B';
        } else if (averagePercentage >= 70) {
            grade = 'C';
        } else if (averagePercentage >= 60) {
            grade = 'D';
        } else {
            grade = 'F';
        }
        System.out.println("\n--- Student Result ---");
        System.out.println("Total Marks: " + totalMarks + " / 300");
        System.out.println("Average Percentage: " + String.format("%.2f", averagePercentage) + "%");
        System.out.println("Grade: " + grade);

        scanner.close();
    }
}
