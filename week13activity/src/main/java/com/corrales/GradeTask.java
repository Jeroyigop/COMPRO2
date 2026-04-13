package com.corrales;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONArray;
import org.json.JSONObject;

public class GradeTask implements Runnable {
    private final String name;
    private final int studentId;
    private final double grade;

    public GradeTask(String name, int studentId, double grade) {
        this.name = name;
        this.studentId = studentId;
        this.grade = grade;
    }

    @Override
    public void run() {
        System.out.println("Processing grade for " + name + " (ID: " + studentId + ")...");

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            System.out.println("Processing interrupted.");
        }

        String remark;
        if (grade >= 90) remark = "Excellent";
        else if (grade >= 75) remark = "Passed";
        else remark = "Failed";

        System.out.println(name + " (ID: " + studentId + ") Grade: " + grade + " → " + remark);

        saveToJSON(name, studentId, grade, remark);
    }

    private synchronized void saveToJSON(String name, int id, double grade, String remark) {
        try {
            JSONArray students;

            try {
                students = new JSONArray(new String(Files.readAllBytes(Paths.get("grades.json"))));
            } catch (IOException e) {
                students = new JSONArray();
            }

            JSONObject student = new JSONObject();
            student.put("name", name);
            student.put("id", id);
            student.put("grade", grade);
            student.put("remark", remark);

            students.put(student);

            try (FileWriter file = new FileWriter("grades.json")) {
                file.write(students.toString(4));
            }

        } catch (IOException e) {
            System.out.println("Error saving JSON.");
        }
    }
}