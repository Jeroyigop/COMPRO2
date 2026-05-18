package com.project;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class MatchHistory {

    public static void saveMatch(
            String player1,
            String player2,
            String winner) {

        File file = new File("history.csv");
        boolean writeHeader = !file.exists() || file.length() == 0;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            if (writeHeader) {
                writer.write("player1,player2,winner");
                writer.newLine();
            }

            writer.write(escapeCsv(player1) + "," +
                    escapeCsv(player2) + "," +
                    escapeCsv(winner));

            writer.newLine();

        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private static String escapeCsv(String value) {
        if (value == null) {
            return "";
        }

        String escaped = value.replace("\"", "\"\"");
        if (escaped.contains(",") || escaped.contains("\n") || escaped.contains("\r") || escaped.contains("\"")) {
            return "\"" + escaped + "\"";
        }

        return escaped;
    }
}