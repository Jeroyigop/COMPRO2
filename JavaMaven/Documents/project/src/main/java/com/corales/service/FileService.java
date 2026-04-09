package com.corales.service;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.corales.model.Player;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class FileService {

    private static final String USERS_FILE = "userinfo.json";
    private Gson gson;

    public FileService() {
        this.gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    }

    public void saveResult(Player p1, Player p2, String winner) {
        // Results are now stored in userinfo.json with player stats
        updateUser(p1);
        updateUser(p2);
    }

    public List<Player> loadUsers() {
        File file = new File(USERS_FILE);

        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (FileReader reader = new FileReader(file)) {
            Type type = new TypeToken<List<Player>>() {
            }.getType();
            List<Player> users = gson.fromJson(reader, type);
            return users != null ? users : new ArrayList<>();
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public void saveUser(Player user) {
        List<Player> users = loadUsers();
        users.add(user);
        saveAllUsers(users);
    }

    public void updateUser(Player updatedPlayer) {
        List<Player> users = loadUsers();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(updatedPlayer.getUsername())) {
                users.set(i, updatedPlayer);
                break;
            }
        }
        saveAllUsers(users);
    }

    private void saveAllUsers(List<Player> users) {
        try (FileWriter writer = new FileWriter(USERS_FILE)) {
            gson.toJson(users, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}