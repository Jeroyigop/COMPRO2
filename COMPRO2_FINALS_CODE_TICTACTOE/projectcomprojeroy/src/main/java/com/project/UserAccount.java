package com.project;

public class UserAccount {

    private String username;
    private String password;
    private int wins;
    private int losses;
    private int ties;

    public UserAccount() {
    }

    public UserAccount(String username, String password) {
        this(username, password, 0, 0, 0);
    }

    public UserAccount(String username, String password, int wins, int losses, int ties) {
        this.username = username;
        this.password = password;
        this.wins = wins;
        this.losses = losses;
        this.ties = ties;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getWins() {
        return wins;
    }

    public int getLosses() {
        return losses;
    }

    public int getTies() {
        return ties;
    }

    public void addWin() {
        wins++;
    }

    public void addLoss() {
        losses++;
    }

    public void addTie() {
        ties++;
    }

    public String getStats() {
        return "Wins=" + wins + ", Losses=" + losses + ", Ties=" + ties;
    }
}
