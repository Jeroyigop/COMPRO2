package com.corales.service;

import com.corales.model.Player;

import java.util.List;

public class AuthService {

    private FileService fileService = new FileService();

    public boolean register(String username, String password) {
        List<Player> users = fileService.loadUsers();

        for (Player user : users) {
            if (user.getUsername().equals(username)) {
                return false; // username already exists
            }
        }

        fileService.saveUser(new Player(username, password));
        return true;
    }

    public Player login(String username, String password) {
        List<Player> users = fileService.loadUsers();

        for (Player user : users) {
            if (user.getUsername().equals(username) &&
                user.getPassword().equals(password)) {
                return user;
            }
        }

        return null; 
    }
}
