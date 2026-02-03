package activity4;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.lang.ArrayIndexOutOfBoundsException;

public class GradeMenu {
    static double[][] grade; 
    static String[] subject;
    static int[] prelim;
    static int[] midterm;
    static int[] Final;

    public static void main(String[] args) {
        subject = new String[50];
        grade = new double[9][9];
        Scanner sc = new Scanner(System.in);

        for (int r = 0; r < 5; r++) {  
            System.out.print("Enter Subject name: ");
            subject[r] = sc.nextLine();

            System.out.print("Enter Prelim: ");
            try {
                grade[r][0] = sc.nextDouble();
            } catch (InputMismatchException e) {
                System.out.println("Invalid number");
            }

            System.out.print("Enter Midterm: ");
            try {
                grade[r][1] = sc.nextDouble();
            } catch (InputMismatchException e) {
                System.out.println("Invalid number");
            }

            System.out.print("Enter Final: ");
            try {
                grade[r][2] = sc.nextDouble();
            } catch (InputMismatchException e) {
                System.out.println("Invalid number");
            }

            sc.nextLine();
            System.out.println();
        }

        writeData();
    }

    public static void writeData() {
        StringBuilder sb = new StringBuilder();

        sb.append("subject,prelim,midterm,Final\n");
        for (int r = 0; r < subject.length; r++) {
            if (grade[r] == null)
                break;

            sb.append(subject[r]);
            for (int c = 0; c < grade[r].length; c++) {
                sb.append(",").append(grade[r][c]);
            }
            sb.append("\n");
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("data.csv"))) {
            bw.write(sb.toString());
            bw.flush();
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }

        System.out.println(sb.toString());
    }
}
