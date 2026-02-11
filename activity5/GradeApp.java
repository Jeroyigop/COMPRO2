package activity5;

import java.io.*;
import java.util.*;

public class GradeApp {

    Scanner sc = new Scanner(System.in);
    List<Grade> grades = new ArrayList<>();

    public static void main(String[] args) {
        GradeApp app = new GradeApp();
        int choice;

        do {
            System.out.println("\nMain Menu:");
            System.out.println("[1] Add Grade");
            System.out.println("[2] Display Grades");
            System.out.println("[3] Exit");
            System.out.print("Choice: ");

            choice = app.sc.nextInt();
            app.sc.nextLine();

            switch (choice) {
                case 1 -> {
                    app.enterGrades();
                    app.writeData();
                }
                case 2 -> {
                    app.readData();
                    app.displayGrades();
                }
                case 3 -> System.out.println("Exiting program...");
                default -> System.out.println("Invalid choice!");
            }
        } while (choice != 3);
    }

    void enterGrades() {
        System.out.print("\nHow many grades to enter? ");
        int count = sc.nextInt();
        sc.nextLine();

        for (int i = 0; i < count; i++) {
            System.out.print("Enter Subject: ");
            String subject = sc.nextLine();

            System.out.print("Enter Prelim: ");
            double prelim = getDouble();

            System.out.print("Enter Midterm: ");
            double midterm = getDouble();

            System.out.print("Enter Finals: ");
            double finals = getDouble();

            grades.add(new Grade(subject, prelim, midterm, finals));
            System.out.println();
        }
    }

    double getDouble() {
        try {
            return sc.nextDouble();
        } catch (InputMismatchException e) {
            System.out.println("Invalid number, set to 0");
            return 0;
        } finally {
            sc.nextLine();
        }
    }

    void writeData() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("data.csv"))) {
            bw.write("Subject,Prelim,Midterm,Finals\n");
            for (Grade g : grades) {
                bw.write(g + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error writing file: " + e.getMessage());
        }
    }

    void readData() {
        grades.clear();
        try (BufferedReader br = new BufferedReader(new FileReader("data.csv"))) {
            br.readLine();
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                grades.add(new Grade(
                        data[0],
                        Double.parseDouble(data[1]),
                        Double.parseDouble(data[2]),
                        Double.parseDouble(data[3])));
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    void displayGrades() {
        System.out.println("\n--- Grades ---");
        if (grades.isEmpty()) {
            System.out.println("No grades available!");
        } else {
            for (Grade g : grades) {
                System.out.printf("%-12s %.2f %.2f %.2f%n",
                        g.subject, g.prelim, g.midterm, g.finals);
            }
        }
    }
}

class Grade {
    String subject;
    double prelim;
    double midterm;
    double finals;

    Grade(String subject, double prelim, double midterm, double finals) {
        this.subject = subject;
        this.prelim = prelim;
        this.midterm = midterm;
        this.finals = finals;
    }

    public String toString() {
        return subject + "," + prelim + "," + midterm + "," + finals;
    }
}