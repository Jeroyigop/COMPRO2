package activity3;
import java.util.Scanner;

public class NestedLoop {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int mainChoice;
        int gradeChoice;

        int[] compro2 = new int[3];
        int[] dsa = new int[3];
        int[] oop = new int[3];

        
        do {
            System.out.println("\nMain Menu:");
            System.out.println("[1] Enter Grade");
            System.out.println("[2] Display Grades");
            System.out.println("[3] Exit");
            System.out.print("Choice: ");
            mainChoice = sc.nextInt();

            if (mainChoice == 1) {

                
                do {
                    System.out.println("\nGrade Menu:");
                    System.out.println("[1] COMPRO2");
                    System.out.println("[2] DSA");
                    System.out.println("[3] OOP");
                    System.out.println("[0] Go back to Main Menu");
                    System.out.print("Choice: ");
                    gradeChoice = sc.nextInt();

                    if (gradeChoice == 1) {
                        System.out.println("Enter grade for COMPRO2:");
                        for (int i = 0; i < 3; i++) {
                            compro2[i] = sc.nextInt();
                        }

                    } else if (gradeChoice == 2) {
                        System.out.println("Enter grade for DSA:");
                        for (int i = 0; i < 3; i++) {
                            dsa[i] = sc.nextInt();
                        }

                    } else if (gradeChoice == 3) {
                        System.out.println("Enter grade for OOP:");
                        for (int i = 0; i < 3; i++) {
                            oop[i] = sc.nextInt();
                        }
                    }

                } while (gradeChoice != 0);
            }
            

            else if (mainChoice == 2) {
                System.out.print("\nCOMPRO2: ");
                for (int i = 0; i < 3; i++) {
                    System.out.print(compro2[i] + " ");
                }


                System.out.print("\nDSA: ");
                for (int i = 0; i < 3; i++) {
                    System.out.print(dsa[i] + " ");
                }


                System.out.print("\nOOP: ");
                for (int i = 0; i < 3; i++) {
                    System.out.print(oop[i] + " ");
                }
                System.out.println();
            }

        } while (mainChoice != 3);

        System.out.println("Program terminated.");
    }
}