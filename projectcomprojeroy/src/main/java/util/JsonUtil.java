package util;

import com.project.Message;
import java.util.ArrayList;
import java.util.List;

/**
 * Minimal JSON helper tailored for the small Message POJO used in this project.
 * This avoids requiring external libraries like Gson for the exercise.
 */
public class JsonUtil {

    public static <T> T fromJson(String json, Class<T> clazz) {
        if (json == null || json.isEmpty())
            return null;

        if (clazz == Message.class) {
            // Very small parser for objects like {"type":"X","content":"Y"}
            String cleaned = json.trim();
            if (cleaned.startsWith("{"))
                cleaned = cleaned.substring(1);
            if (cleaned.endsWith("}"))
                cleaned = cleaned.substring(0, cleaned.length() - 1);

            String type = null;
            String content = null;

            // Split on commas that are not inside quotes
            List<String> partsList = new ArrayList<>();
            StringBuilder sb = new StringBuilder();
            boolean inQuotes = false;
            for (int i = 0; i < cleaned.length(); i++) {
                char ch = cleaned.charAt(i);
                if (ch == '"')
                    inQuotes = !inQuotes;
                if (ch == ',' && !inQuotes) {
                    partsList.add(sb.toString());
                    sb.setLength(0);
                } else {
                    sb.append(ch);
                }
            }
            if (sb.length() > 0)
                partsList.add(sb.toString());

            String[] parts = partsList.toArray(new String[0]);
            for (String part : parts) {
                String[] kv = part.split(":", 2);
                if (kv.length != 2)
                    continue;
                String key = kv[0].trim().replaceAll("^\"|\"$", "");
                String val = kv[1].trim();
                if (val.startsWith("\"") && val.endsWith("\"")) {
                    val = val.substring(1, val.length() - 1);
                }
                val = val.replaceAll("\\\\\"", "\"");

                if ("type".equals(key))
                    type = val;
                if ("content".equals(key))
                    content = val;
            }

            @SuppressWarnings("unchecked")
            T msg = (T) new Message(type, content);
            return msg;
        }

        // Generic fallback: not implemented
        return null;
    }

    public static String toJson(Object object) {
        if (object == null)
            return "null";

        if (object instanceof Message) {
            Message m = (Message) object;
            String type = m.getType() == null ? "" : m.getType().replace("\"", "\\\"");
            String content = m.getContent() == null ? "" : m.getContent().replace("\"", "\\\"");
            return "{\"type\":\"" + type + "\",\"content\":\"" + content + "\"}";
        }

        return object.toString();
    }
}
