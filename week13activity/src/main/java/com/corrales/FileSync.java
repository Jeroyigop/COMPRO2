package com.corrales;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONArray;

public class FileSync implements Runnable {

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                try {
             
                    String content = new String(Files.readAllBytes(Paths.get("grades.json")));
                    JSONArray students = new JSONArray(content);

                    System.out.println("[FileSync] Read " + students.length() + " students from disk.");


                    Files.write(Paths.get("grades.json"), students.toString(4).getBytes());

                    System.out.println("[FileSync] Saved to disk.");

                } catch (IOException e) {
                    System.out.println("[FileSync] No data to sync yet.");
                }

                try {
                    Thread.sleep(50000); 
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        } finally {
            System.out.println("FileSync stopped.");
        }
    }
}
