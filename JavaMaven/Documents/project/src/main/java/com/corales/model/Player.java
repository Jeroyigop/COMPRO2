package com.corales.model;

public class Player {

    private String username;
    private String password;
    private int score;
    private int losses;
    private int totalWins;
    private int totalLosses;

    public Player(String username, String password) {
        this.username = username;
        this.password = password;
        this.score = 0;
        this.losses = 0;
        this.totalWins = 0;
        this.totalLosses = 0;
    }


    public Player(String username) {
        this.username = username;
        this.score = 0;
        this.losses = 0;
        this.totalWins = 0;
        this.totalLosses = 0;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }


    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public int getTotalWins() {
        return totalWins;
    }

    public void setTotalWins(int totalWins) {
        this.totalWins = totalWins;
    }

    public int getTotalLosses() {
        return totalLosses;
    }

    public void setTotalLosses(int totalLosses) {
        this.totalLosses = totalLosses;
    }

    public void addWin() {
        this.totalWins++;
    }

    public void addLoss() {
        this.totalLosses++;
    }
}