package com.project;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class UserDatabase {

    private final File file;
    private final Map<String, UserAccount> accounts;
    private final Gson gson = new Gson();

    public UserDatabase(String fileName) throws IOException {
        this.file = new File(fileName);
        if (!file.exists()) {
            file.createNewFile();
            accounts = new HashMap<>();
            save();
        } else {
            accounts = load();
        }
    }

    private Map<String, UserAccount> load() throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
        }

        String text = content.toString().trim();
        if (text.isEmpty()) {
            return new HashMap<>();
        }

        try {
            return gson.fromJson(text, new TypeToken<Map<String, UserAccount>>(){}.getType());
        } catch (Exception e) {
            System.err.println("Error loading user database: " + e.getMessage());
            return new HashMap<>();
        }
    }

    private void save() {
        try (Writer writer = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8)) {
            writer.write(gson.toJson(accounts));
        } catch (IOException e) {
            System.err.println("Unable to save user database: " + e.getMessage());
        }
    }

    public synchronized boolean registerUser(String username, String password) {
        if (accounts.containsKey(username)) {
            return false;
        }
        accounts.put(username, new UserAccount(username, password));
        save();
        return true;
    }

    public synchronized UserAccount authenticate(String username, String password) {
        UserAccount account = accounts.get(username);
        if (account == null) {
            return null;
        }
        if (account.getPassword().equals(password)) {
            return account;
        }
        return null;
    }

    public synchronized UserAccount getAccount(String username) {
        return accounts.get(username);
    }

    public synchronized void recordResult(String username, char result) {
        UserAccount account = accounts.get(username);
        if (account == null) {
            return;
        }
        switch (result) {
            case 'W' -> account.addWin();
            case 'L' -> account.addLoss();
            case 'T' -> account.addTie();
            default -> {
            }
        }
        save();
    }
}
