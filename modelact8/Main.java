package com.corales.modell;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int mainChoice;
        int gradeChoice;

        String[] subjects = {"COMPRO2", "OOP"};
        String[] exams = {"Prelims", "Midterms", "Finals"};

        double[][] grades = new double[3][3];

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
                    System.out.println("[2] OOP");
                    System.out.println("[0] Go back to Main Menu");
                    System.out.print("Choice: ");
                    gradeChoice = sc.nextInt();

                    if (gradeChoice >= 1 && gradeChoice <= 3) {

                        int subjectIndex = gradeChoice - 1;

                        System.out.println("Enter grades for " + subjects[subjectIndex]);

                        for (int i = 0; i < 3; i++) {
                            System.out.print(exams[i] + ": ");
                            grades[subjectIndex][i] = sc.nextDouble();
                        }
                    }

                } while (gradeChoice != 0);
            } else if (mainChoice == 2) {

                for (int i = 0; i < subjects.length; i++) {

                    System.out.print("\n" + subjects[i] + ": ");

                    for (int j = 0; j < exams.length; j++) {
                        System.out.print(exams[j] + "=" + grades[i][j] + " ");
                    }
                }

                System.out.println();
            }

        } while (mainChoice != 3);

        System.out.println("Program terminated.");
    }
}
