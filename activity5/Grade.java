package activity5;
import java.util.Scanner;

public class Grade {

    static int[] compro2 = new int[3];
    static int[] dsa = new int[3];
    static int[] oop = new int[3];

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int mainChoice;

        do {
            showMainMenu();
            mainChoice = sc.nextInt();

            if (mainChoice == 1) {
                enterGrades();
            } 
            else if (mainChoice == 2) {
                displayGrades();
            }

        } while (mainChoice != 3);

        System.out.println("Program terminated.");
    }



    public static void showMainMenu() {
        System.out.println("\nMain Menu:");
        System.out.println("[1] Enter Grade");
        System.out.println("[2] Display Grades");
        System.out.println("[3] Exit");
        System.out.print("Choice: ");
    }

    public static void enterGrades() {
        int gradeChoice;

        do {
            showGradeMenu();
            gradeChoice = sc.nextInt();

            if (gradeChoice == 1) {
                inputGrades("COMPRO2", compro2);
            } 
            else if (gradeChoice == 2) {
                inputGrades("DSA", dsa);
            } 
            else if (gradeChoice == 3) {
                inputGrades("OOP", oop);
            }

        } while (gradeChoice != 0);
    }

    public static void showGradeMenu() {
        System.out.println("\nGrade Menu:");
        System.out.println("[1] COMPRO2");
        System.out.println("[2] DSA");
        System.out.println("[3] OOP");
        System.out.println("[0] Go back to Main Menu");
        System.out.print("Choice: ");
    }

    public static void inputGrades(String subject, int[] grades) {
        System.out.println("Enter grades for " + subject + ":");
        for (int i = 0; i < grades.length; i++) {
            grades[i] = sc.nextInt();
        }
    }

    public static void displayGrades() {
        printSubject("COMPRO2", compro2);
        printSubject("DSA", dsa);
        printSubject("OOP", oop);
    }

    public static void printSubject(String subject, int[] grades) {
        System.out.print(subject + ": ");
        for (int g : grades) {
            System.out.print(g + " ");
        }
        System.out.println();
    }
}
