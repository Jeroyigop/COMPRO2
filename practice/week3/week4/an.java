package practice.week3.week4;

package activity4;

import java.util.InputMismatchException;
import java.util.Scanner;

public class GradeMenu {

    static double[][] grade = new double[5][3]; // 5 subjects, 3 grades
    static String[] subject = new String[5];

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        for (int r = 0; r < 5; r++) {
            System.out.print("Enter Subject name: ");
            subject[r] = sc.nextLine();

            try {
                System.out.print("Enter Prelim: ");
                grade[r][0] = sc.nextDouble();

                System.out.print("Enter Midterm: ");
                grade[r][1] = sc.nextDouble();

                System.out.print("Enter Final: ");
                grade[r][2] = sc.nextDouble();
            } catch (InputMismatchException e) {
                System.out.println("Invalid number!");
                sc.nextLine();
                r--; // repeat current subject
                continue;
            }

            sc.nextLine(); // clear buffer
            System.out.println();
        }

        displayGrades();
    }

    public static void displayGrades() {
        System.out.println("SUBJECT\t\tPRELIM\tMIDTERM\tFINAL");
        System.out.println("------------------------------------------------");

        for (int i = 0; i < subject.length; i++) {
            System.out.printf(
                "%-10s\t%.2f\t%.2f\t%.2f\n",
                subject[i],
                grade[i][0],
                grade[i][1],
                grade[i][2]
            );
        }
    }
}
