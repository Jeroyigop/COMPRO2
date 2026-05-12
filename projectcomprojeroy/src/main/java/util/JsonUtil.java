package util;

import com.google.gson.Gson;

public class JsonUtil {

    private static final Gson GSON = new Gson();

    public static <T> T fromJson(String json, Class<T> clazz) {
        if (json == null || json.isEmpty()) {
            return null;
        }
        return GSON.fromJson(json, clazz);
    }

    public static String toJson(Object object) {
        return GSON.toJson(object);
    }
}
