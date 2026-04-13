package com.corrales;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

public class GradeOperations {

    public static void viewAllGrades() {
        try {
            String content = new String(Files.readAllBytes(Paths.get("grades.json")));
            JSONArray students = new JSONArray(content);

            if (students.length() == 0) {
                System.out.println("No grades found.");
                return;
            }

            System.out.println("\n=== ALL GRADES ===");
            for (int i = 0; i < students.length(); i++) {
                JSONObject student = students.getJSONObject(i);
                String name = student.optString("name", "N/A");
                System.out.println((i + 1) + ". Name: " + name + 
                                   ", ID: " + student.getInt("id") + 
                                   ", Grade: " + student.getDouble("grade") + 
                                   ", Remark: " + student.getString("remark"));
            }
        } catch (IOException e) {
            System.out.println("No grades file found.");
        }
    }

    public static void searchGrades(Scanner sc) {
        try {
            String content = new String(Files.readAllBytes(Paths.get("grades.json")));
            JSONArray students = new JSONArray(content);

            System.out.print("Search by (1) Name or (2) ID? Enter choice: ");
            int searchChoice = sc.nextInt();
            sc.nextLine();

            boolean found = false;

            switch (searchChoice) {
                case 1:
                    System.out.print("Enter student name to search: ");
                    String searchName = sc.nextLine().toLowerCase();

                    System.out.println("\n=== SEARCH RESULTS ===");
                    for (int i = 0; i < students.length(); i++) {
                        JSONObject student = students.getJSONObject(i);
                        String name = student.optString("name", "");
                        if (name.toLowerCase().contains(searchName)) {
                            System.out.println("Name: " + (name.isEmpty() ? "N/A" : name) + 
                                             ", ID: " + student.getInt("id") + 
                                             ", Grade: " + student.getDouble("grade") + 
                                             ", Remark: " + student.getString("remark"));
                            found = true;
                        }
                    }
                    break;
                case 2:
                    System.out.print("Enter student ID to search: ");
                    int searchId = sc.nextInt();

                    System.out.println("\n=== SEARCH RESULTS ===");
                    for (int i = 0; i < students.length(); i++) {
                        JSONObject student = students.getJSONObject(i);
                        if (student.getInt("id") == searchId) {
                            String name = student.optString("name", "N/A");
                            System.out.println("Name: " + name + 
                                             ", ID: " + student.getInt("id") + 
                                             ", Grade: " + student.getDouble("grade") + 
                                             ", Remark: " + student.getString("remark"));
                            found = true;
                        }
                    }
                    break;
                default:
                    System.out.println("Invalid choice.");
            }

            if (!found) {
                System.out.println("No matching grades found.");
            }
        } catch (IOException e) {
            System.out.println("No grades file found.");
        }
    }

    public static void editGrades(Scanner sc) {
        try {
            String content = new String(Files.readAllBytes(Paths.get("grades.json")));
            JSONArray students = new JSONArray(content);

            System.out.print("Enter student ID to edit: ");
            int editId = sc.nextInt();
            sc.nextLine();

            boolean found = false;
            for (int i = 0; i < students.length(); i++) {
                JSONObject student = students.getJSONObject(i);
                if (student.getInt("id") == editId) {
                    String name = student.optString("name", "Student");
                    System.out.println("Found: " + name);
                    System.out.print("Enter new grade: ");
                    double newGrade = sc.nextDouble();

                    String remark;
                    if (newGrade >= 90) remark = "Excellent";
                    else if (newGrade >= 75) remark = "Passed";
                    else remark = "Failed";

                    student.put("grade", newGrade);
                    student.put("remark", remark);

                    Files.write(Paths.get("grades.json"), students.toString(4).getBytes());
                    System.out.println("Grade updated successfully!");
                    found = true;
                    break;
                }
            }

            if (!found) {
                System.out.println("Student not found.");
            }
        } catch (IOException e) {
            System.out.println("Error editing grades.");
        }
    }
}
