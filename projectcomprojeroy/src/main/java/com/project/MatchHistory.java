package com.project;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MatchHistory {

    /**
     * Append a match record to history.json as a JSON array of objects.
     * If the file does not exist it will be created.
     */
    public static void saveMatch(
            String player1,
            String player2,
            String winner) {

        File file = new File("history.json");

        String entry = "{" +
                "\"player1\":\"" + escapeJson(player1) + "\"," +
                "\"player2\":\"" + escapeJson(player2) + "\"," +
                "\"winner\":\"" + escapeJson(winner) + "\"" +
                "}";

        try {
            if (!file.exists() || file.length() == 0) {
                String initial = "[" + entry + "]";
                Files.write(Paths.get(file.getPath()), initial.getBytes(StandardCharsets.UTF_8));
                return;
            }

            String content = new String(Files.readAllBytes(Paths.get(file.getPath())), StandardCharsets.UTF_8).trim();
            if (content.isEmpty()) {
                String initial = "[" + entry + "]";
                Files.write(Paths.get(file.getPath()), initial.getBytes(StandardCharsets.UTF_8));
                return;
            }

            // naive, but sufficient for this small exercise: insert before final ']'
            String newContent;
            if (content.endsWith("]")) {
                String body = content.substring(0, content.length() - 1).trim();
                if (body.endsWith("[")) {
                    newContent = body + entry + "]";
                } else {
                    newContent = body + "," + entry + "]";
                }
            } else {
                // fallback: overwrite with single-entry array
                newContent = "[" + entry + "]";
            }

            Files.write(Paths.get(file.getPath()), newContent.getBytes(StandardCharsets.UTF_8));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String escapeJson(String value) {
        if (value == null)
            return "";
        String v = value.replace("\\", "\\\\");
        v = v.replace("\"", "\\\"");
        v = v.replace("\n", "\\n");
        v = v.replace("\r", "\\r");
        return v;
    }
}