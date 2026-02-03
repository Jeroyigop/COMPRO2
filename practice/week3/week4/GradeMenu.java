package practice.week3.week4;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class GradeMenu {

    static double[][] grade;
    static String[] subject;
    static int subjectCount = 5;

    public static void main(String[] args) {

        subject = new String[subjectCount];
        grade = new double[subjectCount][5]; // Prelim, Midterm, Final

        Scanner sc = new Scanner(System.in);

        for (int r = 0; r < subjectCount; r++) {

            System.out.print("Enter Subject name: ");
            subject[r] = sc.nextLine();

            grade[r][0] = readGrade(sc, "Enter Prelim: ");
            grade[r][1] = readGrade(sc, "Enter Midterm: ");
            grade[r][2] = readGrade(sc, "Enter Final: ");

            System.out.println();
        }

        writeData();
        sc.close();
    }

    // Method to safely read grades
    private static double readGrade(Scanner sc, String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                double value = sc.nextDouble();
                sc.nextLine(); // clear buffer
                return value;
            } catch (InputMismatchException e) {
                System.out.println("Invalid number. Try again.");
                sc.nextLine(); // clear invalid input
            }
        }
    }

    public static void writeData() {

        StringBuilder sb = new StringBuilder();
        sb.append("Subject,Prelim,Midterm,Final\n");

        for (int r = 0; r < subjectCount; r++) {
            sb.append(subject[r]);
            for (int c = 0; c < 3; c++) {
                sb.append(",").append(grade[r][c]);
            }
            sb.append("\n");
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("data.csv"))) {
            bw.write(sb.toString());
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println("\nSaved data:");
        System.out.println(sb);
    }
}
