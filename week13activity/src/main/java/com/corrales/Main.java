package com.corrales;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        try (Scanner sc = new Scanner(System.in)) {
            Thread greetThread = new Thread(new Greeting());
            greetThread.setDaemon(true);
            greetThread.start();

            Thread fileSyncThread = new Thread(new FileSync());
            fileSyncThread.setDaemon(true);
            fileSyncThread.start();

            while (true) {
                System.out.println("\n=== GRADE MENU ===");
                System.out.println("1. Enter Student Grade");
                System.out.println("2. View Grades");
                System.out.println("3. Search Grades");
                System.out.println("4. Edit Grades");
                System.out.println("5. Exit");
                System.out.print("Choice: ");

                int choice = sc.nextInt();
                sc.nextLine(); 

                switch (choice) {
                    case 1:
                        System.out.print("Enter student name: ");
                        String name = sc.nextLine();

                        int studentId = -1;
                        while (studentId == -1) {
                            try {
                                System.out.print("Enter student ID: ");
                                studentId = sc.nextInt();
                            } catch (InputMismatchException e) {
                                System.out.println("Invalid input! Please enter a valid number.");
                                sc.nextLine(); // Clear the invalid input
                            }
                        }

                        double grade = -1;
                        while (grade == -1) {
                            try {
                                System.out.print("Enter grade: ");
                                grade = sc.nextDouble();
                            } catch (InputMismatchException e) {
                                System.out.println("Invalid input! Please enter a valid number.");
                                sc.nextLine(); 
                            }
                        }

                        Thread t = new Thread(new GradeTask(name, studentId, grade));
                        t.start();
                        break;
                    case 2:
                        GradeOperations.viewAllGrades();
                        break;
                    case 3:
                        GradeOperations.searchGrades(sc);
                        break;
                    case 4:
                        GradeOperations.editGrades(sc);
                        break;
                    case 5:
                        System.out.println("Exiting...");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid choice.");
                }
            }
        }
    }
}