package com.corales.Rps;

import java.io.Serializable;

public class User implements Serializable {
    String username;
    String password;
    int score;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.score = 0;
    }
}
